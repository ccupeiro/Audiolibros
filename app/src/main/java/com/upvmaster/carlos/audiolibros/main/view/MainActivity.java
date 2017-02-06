package com.upvmaster.carlos.audiolibros.main.view;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.login.view.LoginActivity;
import com.upvmaster.carlos.audiolibros.main.view.adapters.AdaptadorLibrosFiltro;
import com.upvmaster.carlos.audiolibros.main.data.BooksRepository;
import com.upvmaster.carlos.audiolibros.main.data.datasources.Libro;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibroSharedPrefenceStorage;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;
import com.upvmaster.carlos.audiolibros.detail.view.DetalleFragment;
import com.upvmaster.carlos.audiolibros.common.view.PreferenciasFragment;
import com.upvmaster.carlos.audiolibros.main.domain.GetLastBook;
import com.upvmaster.carlos.audiolibros.main.domain.HasLastBook;
import com.upvmaster.carlos.audiolibros.main.domain.SaveLastBook;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

    private AdaptadorLibrosFiltro adaptador;
    private MainPresenter presenter;

    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private View headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BooksRepository booksRepository = new BooksRepository(LibroSharedPrefenceStorage.getInstance(this));
        presenter = new MainPresenter(new SaveLastBook(booksRepository),
                new GetLastBook(booksRepository),
                new HasLastBook(booksRepository),
                this);
        int idContenedor = (findViewById(R.id.contenedor_pequeno) != null)
                ? R.id.contenedor_pequeno : R.id.contenedor_izquierdo;
        SelectorFragment primerFragment = new SelectorFragment();
        getFragmentManager().beginTransaction().add(idContenedor, primerFragment).commit();
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adaptador = LibrosSingleton.getInstance(this).getAdaptadorLibros();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //Pestañas
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Todos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(false);
                        break;
                    case 1: //Nuevos
                        adaptador.setNovedad(true);
                        adaptador.setLeido(false);
                        break;
                    case 2: //Leidos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(true);
                        break;
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickFavoriteButton();
            }
        });
        //Nombre de usuario
        headerLayout = navigationView.getHeaderView(0);
        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(String.format(getString(R.string.welcome_message), presenter.getNameUser(this)));
        presenter.colocarImagen(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_preferencias) {
            lanzarPreferencias();
            return true;
        } else if (id == R.id.menu_ultimo) {
            irUltimoVisitado();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void mostrarDetalle(int id) {
        presenter.openDetalle(id);
    }

    @Override
    public void mostrarNoUltimaVisita() {
        Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostrarImagenUser(Uri url, ImageLoader loader) {
        if(url!=null){
            NetworkImageView fotoUser = (NetworkImageView) headerLayout.findViewById(R.id.imageView);
            fotoUser.setImageUrl(url.toString(),loader);
        }
    }

    @Override
    public void mostrarFragmentDetalle(int id) {
        DetalleFragment detalleFragment = (DetalleFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment != null) {
            detalleFragment.ponInfoLibro(id);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getFragmentManager()
                    .beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
    }


    public void irUltimoVisitado() {
        presenter.clickFavoriteButton();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_todos) {
            adaptador.setGenero("");
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_epico) {
            adaptador.setGenero(Libro.G_EPICO);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            adaptador.setGenero(Libro.G_S_XIX);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_suspense) {
            adaptador.setGenero(Libro.G_SUSPENSE);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_preferencias) {
            lanzarPreferencias();
        } else if (id == R.id.nav_signout) {
            presenter.logout(this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void lanzarPreferencias() {
        int idContenedor = (findViewById(R.id.contenedor_pequeno) != null)
                ? R.id.contenedor_pequeno : R.id.contenedor_izquierdo;
        PreferenciasFragment prefFragment = new PreferenciasFragment();
        getFragmentManager().beginTransaction()
                .replace(idContenedor, prefFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void mostrarElementos(boolean mostrar) {
        appBarLayout.setExpanded(mostrar);
        toggle.setDrawerIndicatorEnabled(mostrar);
        if (mostrar) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            tabs.setVisibility(View.VISIBLE);
        } else {
            tabs.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

}
