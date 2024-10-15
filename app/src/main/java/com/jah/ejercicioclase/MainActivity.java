package com.jah.ejercicioclase;



import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    RadioGroup rgEquipos;
    RadioButton rbMadrid, rbBarcelona, rbSevilla, rbBetis;
    EditText txtJugador;
    Button btnAniadir;
    TextView lblNombreJugador, lblValoracion, lblResultado;
    RatingBar rbPuntuacion;
    Spinner spLista;
    ArrayAdapter adaptador;
    HashSet<String> jugadoresMadrid = new HashSet<>();
    HashSet<String> jugadoresBarcelona = new HashSet<>();
    HashSet<String> jugadoresSevilla = new HashSet<>();
    HashSet<String> jugadoresBetis = new HashSet<>();
    HashMap<String, Integer> puntuacionesMadrid = new HashMap<>();
    HashMap<String, Integer> puntuacionesBarcelona = new HashMap<>();
    HashMap<String, Integer> puntuacionesSevilla = new HashMap<>();
    HashMap<String, Integer> puntuacionesBetis= new HashMap<>();
    ArrayList<String> mostrarJugadores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rgEquipos = findViewById(R.id.rgEquipos);
        rbMadrid = findViewById(R.id.rbMadrid);
        rbBarcelona = findViewById(R.id.rbBarcelona);
        rbSevilla = findViewById(R.id.rbSevilla);
        rbBetis = findViewById(R.id.rbBetis);
        txtJugador = findViewById(R.id.txtJugador);
        btnAniadir = findViewById(R.id.btnAniadir);
        lblNombreJugador = findViewById(R.id.lblNombreJugador);
        lblValoracion = findViewById(R.id.lblValoracion);
        lblResultado = findViewById(R.id.lblResultado);
        rbPuntuacion = findViewById(R.id.rbPuntuacion);
        spLista = findViewById(R.id.spLista);

        rbMadrid.setChecked(true);

        mostrarJugadores.addAll(jugadoresMadrid);

        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        spLista.setAdapter(adaptador);

        btnAniadir.setOnClickListener(view -> aniadirJugador());

        rgEquipos.setOnCheckedChangeListener((radioGroup, i) ->  cambiarContenido());

        spLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarMedia();
                cambiarJugador();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       rbPuntuacion.setOnRatingBarChangeListener((ratingBar, v, b) -> {
           cambiarPuntuacion();
           mostrarMedia();
       });

    }

    private void mostrarMedia() {
        String str = "";
        double resultadoPuntuacion = 0.0;
        if(rbMadrid.isChecked()){
            for(int value : puntuacionesMadrid.values()){
                resultadoPuntuacion += value;
            }
            resultadoPuntuacion = resultadoPuntuacion / jugadoresMadrid.size();
            str = "Los jugadores del " + rbMadrid.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbBarcelona.isChecked()){
            for(int value : puntuacionesBarcelona.values()){
                resultadoPuntuacion += value;
            }
            resultadoPuntuacion = resultadoPuntuacion / jugadoresMadrid.size();
            str = "Los jugadores del " + rbBarcelona.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbSevilla.isChecked()){
            for(int value : puntuacionesSevilla.values()){
                resultadoPuntuacion += value;
            }
            resultadoPuntuacion = resultadoPuntuacion / jugadoresMadrid.size();
            str = "Los jugadores del " + rbSevilla.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbBetis.isChecked()){
            for(int value : puntuacionesBetis.values()){
                resultadoPuntuacion += value;
            }
            resultadoPuntuacion = resultadoPuntuacion / jugadoresMadrid.size();
            str = "Los jugadores del " + rbBetis.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }

        lblResultado.setText(str);
    }

    private void cambiarPuntuacion() {
        int puntuacion = 0;
        if(rbMadrid.isChecked()){
            puntuacion = (int) rbPuntuacion.getRating();
            puntuacionesMadrid.put(lblNombreJugador.getText().toString(), puntuacion);
            if(Objects.equals(puntuacionesMadrid.get(lblNombreJugador.getText().toString()), spLista.getSelectedItem())){
                puntuacionesMadrid.replace((String)lblNombreJugador.getText(), (int)rbPuntuacion.getRating());
            }
        }else if(rbBarcelona.isChecked()){
            puntuacion = (int) rbPuntuacion.getRating();
            puntuacionesBarcelona.put(lblNombreJugador.getText().toString(), puntuacion);
            if(Objects.equals(puntuacionesBarcelona.get(lblNombreJugador.getText().toString()), spLista.getSelectedItem())){
                puntuacionesBarcelona.replace((String)lblNombreJugador.getText(), (int)rbPuntuacion.getRating());
            }
        }else if(rbSevilla.isChecked()){
            puntuacion = (int) rbPuntuacion.getRating();
            puntuacionesSevilla.put(lblNombreJugador.getText().toString(), puntuacion);
            if(Objects.equals(puntuacionesSevilla.get(lblNombreJugador.getText().toString()), spLista.getSelectedItem())){
                puntuacionesSevilla.replace((String)lblNombreJugador.getText(), (int)rbPuntuacion.getRating());
            }
        }else if(rbBetis.isChecked()){
            puntuacion = (int) rbPuntuacion.getRating();
            puntuacionesBetis.put(lblNombreJugador.getText().toString(), puntuacion);
            if(Objects.equals(puntuacionesBetis.get(lblNombreJugador.getText().toString()), spLista.getSelectedItem())){
                puntuacionesBetis.replace((String)lblNombreJugador.getText(), (int)rbPuntuacion.getRating());
            }
        }
        int valor = (int) rbPuntuacion.getRating();
        lblValoracion.setText(valor + "");
    }

    private void cambiarJugador() {
        spLista.getSelectedItem();
        String str = spLista.getSelectedItem().toString();
        int valor = (int)rbPuntuacion.getRating();
        lblValoracion.setText("" + valor);
        lblNombreJugador.setText(str);
    }

    private void cambiarContenido() {

        if(rbMadrid.isChecked()){
            mostrarJugadores.clear();
            mostrarJugadores.addAll(jugadoresMadrid);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        }else if(rbBarcelona.isChecked()){
            mostrarJugadores.clear();
            mostrarJugadores.addAll(jugadoresBarcelona);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        }else if(rbSevilla.isChecked()){
            mostrarJugadores.clear();
            mostrarJugadores.addAll(jugadoresSevilla);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        }else if(rbBetis.isChecked()){
            mostrarJugadores.clear();
            mostrarJugadores.addAll(jugadoresBetis);
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        }

        spLista.setAdapter(adaptador);
    }

    private void aniadirJugador() {
        String str = txtJugador.getText().toString();
        if(rbMadrid.isChecked()){
            jugadoresMadrid.add(str);
            cambiarContenido();
        }else if(rbBarcelona.isChecked()){
            jugadoresBarcelona.add(str);
            cambiarContenido();
        }else if(rbSevilla.isChecked()){
            jugadoresSevilla.add(str);
            cambiarContenido();
        }else if(rbBetis.isChecked()){
            jugadoresBetis.add(str);
            cambiarContenido();
        }
    }
}