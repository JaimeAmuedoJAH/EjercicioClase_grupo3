package com.jah.ejercicioclase;



import android.os.Bundle;
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
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    //Declaramos variables y componentes de la aplicación.
    RadioGroup rgEquipos;
    RadioButton rbMadrid, rbBarcelona, rbSevilla, rbBetis;
    EditText txtJugador;
    Button btnAniadir;
    TextView lblNombreJugador, lblValoracion, lblResultado;
    RatingBar rbPuntuacion;
    Spinner spLista;
    ArrayAdapter <String>adaptador;
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

        //Asociamos las variables con los elementos de nuestra app.
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

        //Primer RadioButton marcado y asociamos el primer array con el spinner(En este caso nuestro primer RadioButton es Madrid).
        rbMadrid.setChecked(true);
        mostrarJugadores.addAll(jugadoresMadrid);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mostrarJugadores);
        spLista.setAdapter(adaptador);

        //Listener de nuestro elemento Button para añadir jugadores a uno de los arrays asociados, llamando al método aniadirJugador().
        btnAniadir.setOnClickListener(view -> aniadirJugador());

        //Listener para el RadioGroup para cambiar el contenido del spinner, llamando al método cambiarContenido().
        rgEquipos.setOnCheckedChangeListener((radioGroup, i) ->  cambiarContenido());

        //Listener para el Spinner para seleccionar cada uno de los jugadores creados, llamamos a los métodos mostrarMedia() y cambiarJugador().
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

    /**
     * Este método calcula y muestra la puntuación media de los jugadores
     * del equipo seleccionado.
     * - Determina cuál equipo está seleccionado mediante RadioButtons.
     * - Suma las puntuaciones de todos los jugadores del equipo seleccionado.
     * - Calcula la media dividiendo la suma total entre el número de jugadores.
     * - Si no hay jugadores, la media se establece en 0.
     * - Genera una cadena descriptiva con la puntuación media y la asigna a lblResultado.
     */
    private void mostrarMedia() {
        String str = "";
        double resultadoPuntuacion = 0.0;
        if(rbMadrid.isChecked()){
            for(int value : puntuacionesMadrid.values()){
                resultadoPuntuacion += value;
            }
            if(!jugadoresMadrid.isEmpty()){
                resultadoPuntuacion = resultadoPuntuacion / jugadoresMadrid.size();
            }else{
                resultadoPuntuacion = 0.0;
            }
            str = "Los jugadores del " + rbMadrid.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbBarcelona.isChecked()){
            for(int value : puntuacionesBarcelona.values()){
                resultadoPuntuacion += value;
            }

            if(!jugadoresBarcelona.isEmpty()){
                resultadoPuntuacion = resultadoPuntuacion / jugadoresBarcelona.size();
            }else{
                resultadoPuntuacion = 0.0;
            }
            str = "Los jugadores del " + rbBarcelona.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbSevilla.isChecked()){
            for(int value : puntuacionesSevilla.values()){
                resultadoPuntuacion += value;
            }

            if(!jugadoresSevilla.isEmpty()){
                resultadoPuntuacion = resultadoPuntuacion / jugadoresSevilla.size();
            }else{
                resultadoPuntuacion = 0.0;
            }
            str = "Los jugadores del " + rbSevilla.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }else if(rbBetis.isChecked()){
            for(int value : puntuacionesBetis.values()){
                resultadoPuntuacion += value;
            }

            if(!jugadoresBetis.isEmpty()){
                resultadoPuntuacion = resultadoPuntuacion / jugadoresBetis.size();
            }else{
                resultadoPuntuacion = 0.0;
            }
            str = "Los jugadores del " + rbBetis.getText() + " tienen una media de " + resultadoPuntuacion + " puntos";
        }

        lblResultado.setText(str);
    }

    /**
     * Este método actualiza la puntuación de un jugador en función del equipo seleccionado.
     * - Obtiene la puntuación del RatingBar y la asocia con el nombre del jugador en el equipo correspondiente.
     * - Verifica si la puntuación del jugador es igual a la opción seleccionada en el Spinner.
     * - Si coincide, reemplaza la puntuación existente con la nueva del RatingBar.
     * - Finalmente, actualiza la etiqueta lblValoracion con el valor de la puntuación del RatingBar.
     */
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

    /**
     * Este método actualiza el nombre del jugador y su valoración basándose en la selección del Spinner.
     * - Obtiene el elemento seleccionado del Spinner y lo convierte a una cadena.
     * - Obtiene la valoración actual del RatingBar.
     * - Actualiza el texto de lblValoracion con la puntuación del RatingBar.
     * - Actualiza el texto de lblNombreJugador con el nombre del jugador seleccionado en el Spinner.
     */
    private void cambiarJugador() {
        spLista.getSelectedItem();
        String str = spLista.getSelectedItem().toString();
        int valor = (int)rbPuntuacion.getRating();
        lblValoracion.setText("" + valor);
        lblNombreJugador.setText(str);
    }

    /**
     * Este método actualiza el contenido del Spinner basado en el equipo seleccionado.
     * - Verifica cuál equipo está seleccionado mediante RadioButtons.
     * - Limpia la lista de jugadores mostrados.
     * - Añade a la lista de jugadores mostrados los jugadores del equipo seleccionado.
     * - Crea un nuevo adaptador con la lista de jugadores actualizada.
     * - Establece el adaptador actualizado en el Spinner.
     * - Llama al método mostrarMedia() para actualizar la media de puntuaciones.
     */
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
        mostrarMedia();
    }

    /**
     * Este método añade un nuevo jugador a la lista del equipo seleccionado.
     * - Obtiene el nombre del jugador desde un campo de texto (txtJugador).
     * - Verifica cuál equipo está seleccionado mediante RadioButtons.
     * - Añade el nombre del jugador a la lista correspondiente del equipo.
     * - Llama al método cambiarContenido() para actualizar el contenido del Spinner y mostrar la media.
     * - Limpia el campo de texto (txtJugador).
     */
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
        txtJugador.setText("");
    }
}