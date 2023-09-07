package com.mwiacek.bryly;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

public class Bryly extends Activity {
    final Activity MyActivity = this;
    ArrayAdapter<CharSequence> adapter2;
    Spinner spinner1, spinner2, spinner3;
    CheckBox checkbox1;
    private MyGLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //      WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        mGLView = findViewById(R.id.surfaceView1);
        checkbox1 = findViewById(R.id.checkBox1);

        mGLView.mRenderer = new MyGLRenderer(this, spinner1, spinner2, spinner3, checkbox1);
        mGLView.setEGLContextClientVersion(2);
        mGLView.setRenderer(mGLView.mRenderer);

        // Render the view only when there is a change in the drawing data
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.typybryl1, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGLView.mRenderer.ChangeFigura();
                mGLView.requestRender();
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        adapter2 = ArrayAdapter.createFromResource(
                                MyActivity, R.array.typybryl2, android.R.layout.simple_spinner_item);
                        spinner2.setAdapter(adapter2);
                        spinner2.setEnabled(true);
                        spinner2.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                        adapter2 = ArrayAdapter.createFromResource(
                                MyActivity, R.array.typybryl3, android.R.layout.simple_spinner_item);
                        spinner2.setAdapter(adapter2);
                        spinner2.setEnabled(true);
                        spinner2.setVisibility(View.VISIBLE);
                        break;
                    default:
                        spinner2.setEnabled(false);
                        spinner2.setVisibility(View.INVISIBLE);
                }
                switch (spinner1.getSelectedItemPosition()) {
                    case 2: //ostros�up prawid�owy
                    case 3: //ostros�up pochy�y
                    case 5: //stozek prosty
                    case 6: //stozek pochy�y
                        checkbox1.setEnabled(true);
                        checkbox1.setVisibility(View.VISIBLE);
                        break;
                    default:
                        checkbox1.setEnabled(false);
                        checkbox1.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
			}
        });

        adapter2 = ArrayAdapter.createFromResource(
                this, R.array.typybryl2, android.R.layout.simple_spinner_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGLView.mRenderer.ChangeFigura();
                mGLView.requestRender();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
			}
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.widoki, android.R.layout.simple_spinner_item);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGLView.mRenderer.ChangeFigura();
                mGLView.requestRender();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
			}
        });

        checkbox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mGLView.mRenderer.ChangeFigura();
                mGLView.requestRender();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }
}


