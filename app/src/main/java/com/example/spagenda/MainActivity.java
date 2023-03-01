package com.example.spagenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

    public class MainActivity extends AppCompatActivity {
        private LinearLayout linearLayout;
        private EditText etTarea;
        private TimePicker timePicker;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            linearLayout = findViewById(R.id.linearLayout);
            etTarea = findViewById(R.id.etTarea);
            timePicker = findViewById(R.id.timePicker);

            Button btnAgregar = findViewById(R.id.btnAgregarTa);
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarTarea();
                }
            });
        }

        private void agregarTarea() {
            // Crear la vista personalizada de la tarea
            LinearLayout tareaLayout = new LinearLayout(this);
            tareaLayout.setOrientation(LinearLayout.HORIZONTAL);

            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Eliminar la tarea de la lista
                    linearLayout.removeView(tareaLayout);
                }
            });
            tareaLayout.addView(btnEliminar);

            Button btnEditar = new Button(this);
            btnEditar.setText("Editar");
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Mostrar un cuadro de diálogo para editar la tarea
                    mostrarDialogoEditar(tareaLayout);
                }
            });
            tareaLayout.addView(btnEditar);

            TextView tvTarea = new TextView(this);
            tvTarea.setText(etTarea.getText().toString());
            tvTarea.setTextSize(24);
            tareaLayout.addView(tvTarea);

            TextView tvHora = new TextView(this);
            int hora = timePicker.getHour();
            int minuto = timePicker.getMinute();
            tvHora.setText(hora + ":" + minuto);
            tvHora.setTextSize(24);
            tareaLayout.addView(tvHora);

            // Agregar la vista personalizada de la tarea al LinearLayout principal
            linearLayout.addView(tareaLayout);

            // Limpiar los campos para agregar una nueva tarea
            etTarea.setText("");
        }

        private void editarTarea(final TextView tvTarea) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Editar tarea");

            // Agregar un LinearLayout para contener los elementos de edición
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            // Agregar un EditText para la tarea
            final EditText etTarea = new EditText(this);
            etTarea.setText(tvTarea.getText().toString());
            linearLayout.addView(etTarea);

            // Agregar un TimePicker para la hora de la tarea
            final TimePicker timePicker = new TimePicker(this);
            String[] tiempo = tvTarea.getTag().toString().split(":");
            timePicker.setHour(Integer.parseInt(tiempo[0]));
            timePicker.setMinute(Integer.parseInt(tiempo[1]));
            linearLayout.addView(timePicker);

            builder.setView(linearLayout);

            // Agregar botón de guardar
            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String tarea = etTarea.getText().toString();

                    // Actualizar la hora de la tarea
                    int hora = timePicker.getHour();
                    int minuto = timePicker.getMinute();

                    String horaFormateada = String.format("%02d:%02d", hora, minuto);
                    tvTarea.setText(tarea);
                    tvTarea.setTag(horaFormateada);
                }
            });

            // Agregar botón de cancelar
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            builder.show();
        }


        private void mostrarDialogoEditar(final LinearLayout tareaLayout) {
            // Obtener el texto y la hora de la tarea actual
            final TextView tvTarea = (TextView) tareaLayout.getChildAt(2);
            final String tarea = tvTarea.getText().toString();
            final String hora = ((TextView) tareaLayout.getChildAt(3)).getText().toString();

            // Crear la vista personalizada del diálogo
            LinearLayout dialogLayout = new LinearLayout(this);
            dialogLayout.setOrientation(LinearLayout.VERTICAL);

            // Agregar un EditText para la tarea
            final EditText etTarea = new EditText(this);
            etTarea.setText(tarea);
            dialogLayout.addView(etTarea);

            // Agregar un TimePicker para la hora de la tarea
            final TimePicker timePicker = new TimePicker(this);
            String[] tiempo = hora.split(":");
            timePicker.setHour(Integer.parseInt(tiempo[0]));
            timePicker.setMinute(Integer.parseInt(tiempo[1]));
            dialogLayout.addView(timePicker);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Editar tarea");
            builder.setView(dialogLayout);

            builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Actualizar la tarea con los nuevos valores
                    String nuevaTarea = etTarea.getText().toString();
                    int hora = timePicker.getHour();
                    int minuto = timePicker.getMinute();
                    String horaFormateada = String.format("%02d:%02d", hora, minuto);

                    tvTarea.setText(nuevaTarea);
                    ((TextView) tareaLayout.getChildAt(3)).setText(horaFormateada);
                }
            });

            builder.setNegativeButton("Cancelar", null);
            builder.show();
        }
    }