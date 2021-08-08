package com.mwiacek.bryly;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.widget.CheckBox;
import android.widget.Spinner;

public class MyGLRenderer implements GLSurfaceView.Renderer {	
	public float xAngle=0.0f, yAngle=45.0f;
	Spinner spinner1,spinner2,spinner3;
	CheckBox checkbox1;
	private float[] mModelMatrix = new float[16];
	private float[] mViewMatrix = new float[16];
	private float[] mProjectionMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	Activity myActivity;    
    int mProgram, mProgram2;
    FloatBuffer vertexBuffer=null;
    ShortBuffer indexBuffer=null;        
    FloatBuffer colorBuffer=null;
    float color[]; // Set color with red, green, blue and alpha (opacity) values
    
    public static final String vertexProgram =
    		"attribute vec4 vPosition; " +
			"uniform mat4 uMVPMatrix; " +
			"void main() { gl_Position =  uMVPMatrix*vPosition; }";
   
    public static final String fragmentProgram =
		   "precision mediump float;" +
		   "uniform vec4 vColor;"+
           "void main() { gl_FragColor = vColor; }";

    public static final String vertexProgram2 =
    		"attribute vec4 vPosition; attribute vec4 a_Color; " +
			"uniform mat4 uMVPMatrix; varying vec4 vColor;" +
			"void main() { gl_Position =  uMVPMatrix*vPosition; vColor = a_Color; }";
   
    public static final String fragmentProgram2 =
		   "precision mediump float;" +
		   "varying vec4 vColor;"+
           "void main() { gl_FragColor = vColor; }";

    public void ChangeFigura() {
        float vertices[]=null;
        short[] indices=null;
        int ilosc=0,num=0, i = 0;
        ByteBuffer bb,ibb;        
        float GoraX = 0.0f, GoraY = 0.0f, promien;
              
        if (vertexBuffer!=null) {
        	vertexBuffer.clear();
        }
        if (indexBuffer!=null) {
        	indexBuffer.clear();
        }
        if (colorBuffer!=null) {
        	colorBuffer.clear();
        }
        
        if (spinner3.getSelectedItemPosition()==0) {
            switch (spinner1.getSelectedItemPosition()) {
            case 0: //graniastos³up prawid³owy
            case 1: //graniastos³up pochy³y	
                ilosc=spinner2.getSelectedItemPosition()+3;

                if (spinner1.getSelectedItemPosition()==1) {
                    GoraX = 0.2f;
                    GoraY = 0.4f;            
                }
                
                vertices = new float[ilosc*6*3];
           	       	
                for (i=0;i<ilosc;i++) {
                	//linia dolnej podstawy
               	 	vertices[i*18+0] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
               	 	vertices[i*18+1] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
               	 	vertices[i*18+2] = 0.0f;
                	vertices[i*18+3] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[i*18+4] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[i*18+5] = 0.0f;

               	 	//linia boczna od do³u do góry
               	 	vertices[i*18+6] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[i*18+7] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[i*18+8] = 0.0f;
               	 	vertices[i*18+9] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() +GoraX;
            	 	vertices[i*18+10] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
            	 	vertices[i*18+11] = 1.0f;

            	 	
               	 	//linia górnej podstawy
                	vertices[i*18+12] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX ;
               	 	vertices[i*18+13] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
               	 	vertices[i*18+14] = 1.0f;
               	 	vertices[i*18+15] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX ;
               	 	vertices[i*18+16] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY;
               	 	vertices[i*18+17] = 1.0f;           	 	
                }
            	
                break;
            case 2: //ostroslup prawidlowy
            case 3: //ostroslup pochyly
            //normalnie moznaby tu dodac stozek, ale nie jest to zrobione, bo linie boczne
            //maja byc co iles	
                ilosc=spinner2.getSelectedItemPosition()+3;
                
                if (spinner1.getSelectedItemPosition()==3) {
                    GoraX = 0.2f;
                    GoraY = 0.4f;            
                }
                
                if (!checkbox1.isChecked()) {                
                    vertices = new float[ilosc*4*3];
               	       	
                    for (i=0;i<ilosc;i++) {
                    	//linia podstawy
                    	vertices[i*12+0] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
                   	 	vertices[i*12+1] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
                   	 	vertices[i*12+2] = 0.0f;
                   	 	vertices[i*12+3] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
                   	 	vertices[i*12+4] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
                   	 	vertices[i*12+5] = 0.0f;
                   	 	
                   	 	//linia boczna od dolu do góry
                   	 	vertices[i*12+6] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
                   	 	vertices[i*12+7] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
                   	 	vertices[i*12+8] = 0.0f;
                		vertices[i*12+9] = GoraX ;
                		vertices[i*12+10] = GoraY;
                		vertices[i*12+11] = 1.0f;
                    }        		
            	} else {
                    vertices = new float[ilosc*6*3];
               	       	
                    for (i=0;i<ilosc;i++) {
                    	//linia podstawy
                    	vertices[i*18+0] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
                   	 	vertices[i*18+1] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
                   	 	vertices[i*18+2] = 0.0f;
                   	 	vertices[i*18+3] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
                   	 	vertices[i*18+4] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
                   	 	vertices[i*18+5] = 0.0f;
                   	 	
                   	 	//linia boczna od dolu do góry
                   	 	vertices[i*18+6] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
                   	 	vertices[i*18+7] = 0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
                   	 	vertices[i*18+8] = 0.0f;
                		vertices[i*18+9] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX*0.6f ;
                		vertices[i*18+10] = 0.4f*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY*0.6f;
                		vertices[i*18+11] = 0.6f;
                		
                		//linia gornej podstawy
                    	vertices[i*18+12] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX*0.6f ;
                   	 	vertices[i*18+13] = 0.4f*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY*0.6f;
                   	 	vertices[i*18+14] = 0.6f;
                   	 	vertices[i*18+15] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX*0.6f ;
                   	 	vertices[i*18+16] = 0.4f*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY*0.6f;
                   	 	vertices[i*18+17] = 0.6f;
                   	 	
                    }        		
            	}
            	
                break;
            case 4: //prostopad³oœcian
                vertices = new float[4*6*3];
           	   
                i = 0;            	            
                double start = (90-15)* 2*Math.PI / 360;
                double end = (90+15)* 2*Math.PI / 360; 
                //linia dolnej podstawy
               	vertices[i*18+0] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+1] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+2] = 0.0f;
                vertices[i*18+3] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+4] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+5] = 0.0f;
               	//linia boczna od do³u do góry
               	vertices[i*18+6] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+7] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+8] = 0.0f;
               	vertices[i*18+9] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
            	vertices[i*18+10] = 0.3f* ((Number)Math.cos(start)).floatValue();
            	vertices[i*18+11] = 1.0f;        	 	
               	//linia górnej podstawy
                vertices[i*18+12] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+13] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+14] = 1.0f;
               	vertices[i*18+15] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+16] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+17] = 1.0f;           	 	

                i = 1;            	            
                start = (90+15)* 2*Math.PI / 360;
                end = (270-15)* 2*Math.PI / 360; 
                //linia dolnej podstawy
               	vertices[i*18+0] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+1] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+2] = 0.0f;
                vertices[i*18+3] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+4] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+5] = 0.0f;
               	//linia boczna od do³u do góry
               	vertices[i*18+6] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+7] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+8] = 0.0f;
               	vertices[i*18+9] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
            	vertices[i*18+10] = 0.3f* ((Number)Math.cos(start)).floatValue();
            	vertices[i*18+11] = 1.0f;        	 	
               	//linia górnej podstawy
                vertices[i*18+12] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+13] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+14] = 1.0f;
               	vertices[i*18+15] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+16] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+17] = 1.0f;           	 	

                i = 2;            	            
                start = (270-15)* 2*Math.PI / 360;
                end = (270+15)* 2*Math.PI / 360; 
                //linia dolnej podstawy
               	vertices[i*18+0] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+1] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+2] = 0.0f;
                vertices[i*18+3] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+4] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+5] = 0.0f;
               	//linia boczna od do³u do góry
               	vertices[i*18+6] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+7] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+8] = 0.0f;
               	vertices[i*18+9] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
            	vertices[i*18+10] = 0.3f* ((Number)Math.cos(start)).floatValue();
            	vertices[i*18+11] = 1.0f;        	 	
               	//linia górnej podstawy
                vertices[i*18+12] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+13] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+14] = 1.0f;
               	vertices[i*18+15] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+16] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+17] = 1.0f;           	 	
               	
                i = 3;            	            
                start = (270+15)* 2*Math.PI / 360;
                end = (90-15)* 2*Math.PI / 360; 
                //linia dolnej podstawy
               	vertices[i*18+0] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+1] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+2] = 0.0f;
                vertices[i*18+3] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+4] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+5] = 0.0f;
               	//linia boczna od do³u do góry
               	vertices[i*18+6] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+7] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+8] = 0.0f;
               	vertices[i*18+9] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
            	vertices[i*18+10] = 0.3f* ((Number)Math.cos(start)).floatValue();
            	vertices[i*18+11] = 1.0f;        	 	
               	//linia górnej podstawy
                vertices[i*18+12] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
               	vertices[i*18+13] = 0.3f* ((Number)Math.cos(start)).floatValue();
               	vertices[i*18+14] = 1.0f;
               	vertices[i*18+15] = 0.3f* ((Number)Math.sin(end)).floatValue() ;
               	vertices[i*18+16] = 0.3f* ((Number)Math.cos(end)).floatValue();
               	vertices[i*18+17] = 1.0f;           	 	

               	break;
            case 5: //stozek prosty
            case 6: //stozek pochyly
                if (spinner1.getSelectedItemPosition()==6) {
                    GoraX = 0.2f;
                    GoraY = 0.4f;            
                }
                
                promien = 0.4f;
                if (spinner2.getSelectedItemPosition()==1) {
                	promien = 0.8f;
                }
                
                if (!checkbox1.isChecked()) {
                    vertices = new float[(720+20)*6];

                    for (i=0;i<720;i++) {
                		//linia podstawy
                		vertices[i*6+0] = 0.4f* ((Number)Math.sin(i* 2*Math.PI / 360)).floatValue() ;
               	 		vertices[i*6+1] = promien* ((Number)Math.cos(i* 2*Math.PI / 360)).floatValue();
               	 		vertices[i*6+2] = 0.0f;
               	 		vertices[i*6+3] = 0.4f* ((Number)Math.sin((i+1)* 2*Math.PI / 360)).floatValue() ;
               	 		vertices[i*6+4] = promien* ((Number)Math.cos((i+1)* 2*Math.PI/360)).floatValue();
               	 		vertices[i*6+5] = 0.0f;
                	}
                    for (i=720;i<720+20;i++) {
                   	 	//linia boczna od dolu do góry
                   	 	vertices[i*6+0] = 0.4f* ((Number)Math.sin(2*Math.PI/20*i)).floatValue() ;
                   	 	vertices[i*6+1] = promien* ((Number)Math.cos(2*Math.PI/20*i)).floatValue();
                   	 	vertices[i*6+2] = 0.0f;
                		vertices[i*6+3] = GoraX ;
                		vertices[i*6+4] = GoraY;
                		vertices[i*6+5] = 1.0f;
                    }            	
                    
                } else {
                    vertices = new float[(720+720+20)*6];

                	for (i=0;i<720;i++) {
                		//linia podstawy
                		vertices[i*6+0] = 0.4f* ((Number)Math.sin(i* 2*Math.PI / 360)).floatValue() ;
               	 		vertices[i*6+1] = promien* ((Number)Math.cos(i* 2*Math.PI / 360)).floatValue();
               	 		vertices[i*6+2] = 0.0f;
               	 		vertices[i*6+3] = 0.4f* ((Number)Math.sin((i+1)* 2*Math.PI / 360)).floatValue() ;
               	 		vertices[i*6+4] = promien* ((Number)Math.cos((i+1)* 2*Math.PI/360)).floatValue();
               	 		vertices[i*6+5] = 0.0f;
                	}
                    for (i=720;i<720+20;i++) {
                   	 	//linia boczna od dolu do góry
                   	 	vertices[i*6+0] = 0.4f* ((Number)Math.sin(2*Math.PI/20*i)).floatValue() ;
                   	 	vertices[i*6+1] = promien* ((Number)Math.cos(2*Math.PI/20*i)).floatValue();
                   	 	vertices[i*6+2] = 0.0f;
                   	 	vertices[i*6+3] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/20*i)).floatValue()+GoraX*0.6f ;
            	 		vertices[i*6+4] = promien*0.4f* ((Number)Math.cos(2*Math.PI/20*i)).floatValue()+GoraY*0.6f;
            	 		vertices[i*6+5] = 0.6f;            	 		
                    }
                    for (i=720+20;i<720+720+20;i++) {
                		//linia gornej podstawy
                		vertices[i*6+0] = 0.4f*0.4f* ((Number)Math.sin((i)* 2*Math.PI / 360)).floatValue()+GoraX*0.6f  ;
               	 		vertices[i*6+1] = promien*0.4f* ((Number)Math.cos((i)* 2*Math.PI / 360)).floatValue()+GoraY*0.6f ;
               	 		vertices[i*6+2] = 0.6f;
               	 		vertices[i*6+3] = 0.4f*0.4f* ((Number)Math.sin((i+1)* 2*Math.PI / 360)).floatValue()+GoraX*0.6f  ;
               	 		vertices[i*6+4] = promien*0.4f* ((Number)Math.cos((i+1)* 2*Math.PI/360)).floatValue()+GoraY*0.6f ;
               	 		vertices[i*6+5] = 0.6f;
                	}                    
                    
                }
                break;            
               	
            case 7: //szeœcian
                ilosc=4;

                vertices = new float[ilosc*6*3];
           	       	
                for (i=0;i<ilosc;i++) {
                	//linia dolnej podstawy
               	 	vertices[i*18+0] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
               	 	vertices[i*18+1] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
               	 	vertices[i*18+2] = 0.0f;
                	vertices[i*18+3] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[i*18+4] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[i*18+5] = 0.0f;

               	 	//linia boczna od do³u do góry
               	 	vertices[i*18+6] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[i*18+7] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[i*18+8] = 0.0f;
               	 	vertices[i*18+9] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() +GoraX;
            	 	vertices[i*18+10] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
            	 	vertices[i*18+11] = (float) Math.sqrt(2*0.3f*0.3f);

            	 	
               	 	//linia górnej podstawy
                	vertices[i*18+12] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX ;
               	 	vertices[i*18+13] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
               	 	vertices[i*18+14] = (float) Math.sqrt(2*0.3f*0.3f);
               	 	vertices[i*18+15] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX ;
               	 	vertices[i*18+16] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY;
               	 	vertices[i*18+17] = (float) Math.sqrt(2*0.3f*0.3f);           	 	
                }
                break;               
            case 8: //walec prosty
            case 9: //walec pochyly
                vertices = new float[(720*2+20)*6];

                promien = 0.4f;
                if (spinner2.getSelectedItemPosition()==1) {
                	promien = 0.8f;
                }
                
                if (spinner1.getSelectedItemPosition()==9) {
                    GoraX = 0.2f;
                    GoraY = 0.4f;            
                }
               

                for (i=0;i<720;i++) {
                	//linia podstawy
                	vertices[i*12+0] = 0.4f* ((Number)Math.sin(i* 2*Math.PI / 360)).floatValue() ;
               	 	vertices[i*12+1] = promien* ((Number)Math.cos(i* 2*Math.PI / 360)).floatValue();
               	 	vertices[i*12+2] = 0.0f;
               	 	vertices[i*12+3] = 0.4f* ((Number)Math.sin((i+1)* 2*Math.PI / 360)).floatValue() ;
               	 	vertices[i*12+4] = promien* ((Number)Math.cos((i+1)* 2*Math.PI/360)).floatValue();
               	 	vertices[i*12+5] = 0.0f;
                	//linia podstawy
                	vertices[i*12+6] = 0.4f* ((Number)Math.sin(i* 2*Math.PI / 360)).floatValue() +GoraX;
               	 	vertices[i*12+7] = promien* ((Number)Math.cos(i* 2*Math.PI / 360)).floatValue()+GoraY;
               	 	vertices[i*12+8] = 1.0f;
               	 	vertices[i*12+9] = 0.4f* ((Number)Math.sin((i+1)* 2*Math.PI / 360)).floatValue()+GoraX ;
               	 	vertices[i*12+10] = promien* ((Number)Math.cos((i+1)* 2*Math.PI/360)).floatValue()+GoraY;
               	 	vertices[i*12+11] = 1.0f;
                }
                
                for (i=720*2;i<720*2+20;i++) {
               	 	//linia boczna od do³u do góry
               	 	vertices[i*6+0] = 0.4f* ((Number)Math.sin(2*Math.PI/20*i)).floatValue() ;
               	 	vertices[i*6+1] = promien* ((Number)Math.cos(2*Math.PI/20*i)).floatValue();
               	 	vertices[i*6+2] = 0.0f;
               	 	vertices[i*6+3] = 0.4f* ((Number)Math.sin(2*Math.PI/20*i)).floatValue() +GoraX;
               	 	vertices[i*6+4] = promien* ((Number)Math.cos(2*Math.PI/20*i)).floatValue()+GoraY;
            		vertices[i*6+5] = 1.0f;
                }
                break;            
            default:
            	break;
            }    	

            // The vertex buffer.
            bb = ByteBuffer.allocateDirect(vertices.length*4 );//byteperfloat=4
            bb.order(ByteOrder.nativeOrder());
            vertexBuffer = bb.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);
            
            return;
        }
        
  
        switch (spinner1.getSelectedItemPosition()) {
        case 0: //graniastos³up prawid³owy
        case 1: //graniastos³up pochy³y	
            ilosc=spinner2.getSelectedItemPosition()+3;

            if (spinner1.getSelectedItemPosition()==1) {
                GoraX = 0.2f;
                GoraY = 0.4f;            
            }
            
            vertices = new float[(ilosc+1)*18+6];
               
        	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
        	
        	vertices[num++] = 0.0f+GoraX;
       	 	vertices[num++] = 0.0f+GoraY;
       	 	vertices[num++] = 1.0f;

       	 	for (i=0;i<=ilosc;i++) {
       	 		//na dol
       	 		vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	
           	 	//na gore
            	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX ;
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
        	 	vertices[num++] = 1.0f;
        	 	
        	 	//na boki
            	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX ;
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
        	 	vertices[num++] = 1.0f;        	 	
        	
        	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX ;
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY;
        	 	vertices[num++] = 1.0f;
            }


            color = new float[((ilosc+1)*6+2)*4];

            num =0;

        	color[num++] = 1f;
        	color[num++] = 0f;
        	color[num++] = 0f;
        	color[num++] = 1f; 

        	color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
            
        	for (i=0;i<=ilosc;i++) {
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 

            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f; 

            	for (int j=0;j<4;j++) {
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f; 
            		
            	}
            }
            
       	 	indices = new short[(ilosc)*12];

       	 	num=0;

      		for (i=0;i<ilosc;i++) {
      			//1 trojkat boku
      			indices[num++] = (short) (2+i*6+3);
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] = (short) (2+i*6+2);
        	
        		//2 trojkat boku
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] =(short) (2+i*6+4);
        		indices[num++] =(short) (2+i*6+2);
      		}
      	 	for (i=0;i<ilosc;i++) {
            	//dolna podstawa
            	indices[num++] = (short) (2+i*6+6);
            	indices[num++] = (short) (2+i*6+0);
            	indices[num++] = 0;
       	 	}
      		for (i=0;i<ilosc;i++) {
            	//gorna podstawa
            	indices[num++] = (short)(2+i*6+7);
            	indices[num++] = (short)(2+i*6+1);
            	indices[num++] = 1;
       	 	}
            	            
            break;
        case 2: //ostros³up prawid³owy
        case 3: //ostros³up pochy³y
        case 5: //stozek prosty
        case 6: //stozek pochyly
        	promien=0.4f;
        	if (spinner1.getSelectedItemPosition()>3) {
        		ilosc=720;
        		if (spinner2.getSelectedItemPosition()==1) {
        			promien=0.8f;
        		}
        	} else {
        		ilosc=spinner2.getSelectedItemPosition()+3;
        	}
            
            if (spinner1.getSelectedItemPosition()==3 || spinner1.getSelectedItemPosition()==6) {
                GoraX = 0.2f;
                GoraY = 0.4f;            
            }
            
            if (!checkbox1.isChecked()) {                
                vertices = new float[(ilosc+1)*12+3];
       	       	
                vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.0f;
                
           	 	for (i=0;i<=ilosc;i++) {
                	//na dol
                	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[num++] = 0.0f;
               	
               	 	//na bok
                  	vertices[num++] = 0.0f+GoraX;
             	 	vertices[num++] = 0.0f+GoraY;
             	 	vertices[num++] = 1.0f;
               	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[num++] = 0.0f;
               	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
               	 	vertices[num++] = 0.0f;
                }

                color = new float[((ilosc+1)*4+1)*4];

                num =0;
            	
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 

            	if (spinner1.getSelectedItemPosition()>3) {
            		
                	for (i=0;i<=360;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<3;j++) {
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }
                	for (i=0;i<360;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<3;j++) {
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }

            	} else {
                	for (i=0;i<=ilosc;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<3;j++) {
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }            		
            	}
                
           	 	indices = new short[(ilosc)*6];

           	 	num=0;

          		for (i=0;i<ilosc;i++) {
          			//trojkat boku
          			indices[num++] = (short) (1+i*4+1);
            		indices[num++] = (short) (1+i*4+2);
            		indices[num++] = (short) (1+i*4+3);
          		}
          	 	for (i=0;i<ilosc;i++) {
                	//dolna podstawa
                	indices[num++] = (short) (1+i*4+4);
                	indices[num++] = (short) (1+i*4+0);
                	indices[num++] = 0;
           	 	}
        	} else {
                vertices = new float[(ilosc+1)*18+6];
                
            	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.0f;
            	
            	vertices[num++] = 0.0f+GoraX*0.6f;
           	 	vertices[num++] = 0.0f+GoraY*0.6f;
           	 	vertices[num++] = 0.6f;

           	 	for (i=0;i<=ilosc;i++) {
           	 		//na dol
           	 		vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[num++] = 0.0f;
               	 	
               	 	//na gore
                	vertices[num++] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX*0.6f;
            	 	vertices[num++] = promien*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY*0.6f;
            	 	vertices[num++] = 0.6f;
            	 	
            	 	//na boki
                	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
               	 	vertices[num++] = 0.0f;
               	 	vertices[num++] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX*0.6f;
            	 	vertices[num++] = promien*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY*0.6f;
            	 	vertices[num++] = 0.6f;        	 	
            	
            	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
               	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
               	 	vertices[num++] = 0.0f;
               	 	vertices[num++] = 0.4f*0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX*0.6f;
            	 	vertices[num++] = promien*0.4f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY*0.6f;
            	 	vertices[num++] = 0.6f;
                }


                color = new float[((ilosc+1)*6+2)*4];

                num =0;

                color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 
                

                color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	
            	if (spinner1.getSelectedItemPosition()>3) {
                	for (i=0;i<=360;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<4;j++) {
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }
                	for (i=0;i<360;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<4;j++) {
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f/(ilosc+5)*(360-i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }
            		
            	} else {
                	for (i=0;i<=ilosc;i++) {
                    	color[num++] = 1f;
                    	color[num++] = 0f;
                    	color[num++] = 0f;
                    	color[num++] = 1f; 

                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f;
                    	color[num++] = 1f; 

                    	for (int j=0;j<4;j++) {
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f/(ilosc+5)*(i+2);
                        	color[num++] = 1f;                 		
                    	}
                    }
            		
            	}
                
           	 	indices = new short[(ilosc)*12];

           	 	num=0;

          		for (i=0;i<ilosc;i++) {
          			//1 trojkat boku
          			indices[num++] = (short) (2+i*6+3);
            		indices[num++] = (short) (2+i*6+5);
            		indices[num++] = (short) (2+i*6+2);
            	
            		//2 trojkat boku
            		indices[num++] = (short) (2+i*6+5);
            		indices[num++] =(short) (2+i*6+4);
            		indices[num++] =(short) (2+i*6+2);
          		}
          	 	for (i=0;i<ilosc;i++) {
                	//dolna podstawa
                	indices[num++] = (short) (2+i*6+6);
                	indices[num++] = (short) (2+i*6+0);
                	indices[num++] = 0;
           	 	}
          		for (i=0;i<ilosc;i++) {
                	//gorna podstawa
                	indices[num++] = (short)(2+i*6+7);
                	indices[num++] = (short)(2+i*6+1);
                	indices[num++] = 1;
           	 	}
        	}

            break;
        case 4: //prostopad³oœcian
            vertices = new float[12*6];
       	   
            num = 0;

            //dol
            double start = (90-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
            start = (90+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
            start = (270-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
            start = (270+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;

           	//gora
            start = (90-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (90+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (270-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (270+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
           	
           	//boczki
            start = (90-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (90+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (270-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (270+15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
            start = (90-15)* 2*Math.PI / 360;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 1.0f;
           	vertices[num++] = 0.3f* ((Number)Math.sin(start)).floatValue() ;
           	vertices[num++] = 0.3f* ((Number)Math.cos(start)).floatValue();
           	vertices[num++] = 0.0f;


           	num=0;
           	
            color = new float[24*4];
            for(i=0;i<4;i++) {            	
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f;
            }
            for(i=0;i<4;i++) {            
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f; 
            }
            for(int j=0;j<4;j++) {
                for(i=0;i<4;i++) {            
                	color[num++] = 1f/(9)*(j+2);
                	color[num++] = 1f/(9)*(j+2);
                	color[num++] = 1f/(9)*(j+2);
                	color[num++] = 1f;             	
                }
            }
           	
       	 	indices = new short[6*6];

       	 	num=0;

      		for (i=0;i<6;i++) {
      			//1 trojkat
      			indices[num++] = (short) (i*4+0);
        		indices[num++] = (short) (i*4+1);
        		indices[num++] = (short) (i*4+2);
        	
        		//2 trojkat
        		indices[num++] = (short) (i*4+2);
        		indices[num++] =(short) (i*4+3);
        		indices[num++] =(short) (i*4+0);
      		}
      		
           	
           	break;
        case 7: //szeœcian
            ilosc=4;
            
            vertices = new float[(ilosc+1)*18+6];
               
        	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
        	
        	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
       	 	vertices[num++] = (float) Math.sqrt(2*0.3f*0.3f);

       	 	for (i=0;i<=ilosc;i++) {
       	 		//na dol
       	 		vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	
           	 	//na gore
            	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue();
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
        	 	vertices[num++] = (float) Math.sqrt(2*0.3f*0.3f);
        	 	
        	 	//na boki
            	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue();
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
        	 	vertices[num++] = (float) Math.sqrt(2*0.3f*0.3f);        	 	
        	
        	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
           	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.3f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue();
        	 	vertices[num++] = 0.3f* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
        	 	vertices[num++] = (float) Math.sqrt(2*0.3f*0.3f);
            }


            color = new float[((ilosc+1)*6+2)*4];

            num =0;

        	color[num++] = 1f;
        	color[num++] = 0f;
        	color[num++] = 0f;
        	color[num++] = 1f; 

        	color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
            
        	for (i=0;i<=ilosc;i++) {
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 

            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f; 

            	for (int j=0;j<4;j++) {
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f;             		
            	}
            }
            
       	 	indices = new short[(ilosc)*12];

       	 	num=0;

      		for (i=0;i<ilosc;i++) {
      			//1 trojkat boku
      			indices[num++] = (short) (2+i*6+3);
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] = (short) (2+i*6+2);
        	
        		//2 trojkat boku
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] =(short) (2+i*6+4);
        		indices[num++] =(short) (2+i*6+2);
      		}
      	 	for (i=0;i<ilosc;i++) {
            	//dolna podstawa
            	indices[num++] = (short) (2+i*6+6);
            	indices[num++] = (short) (2+i*6+0);
            	indices[num++] = 0;
       	 	}
      		for (i=0;i<ilosc;i++) {
            	//gorna podstawa
            	indices[num++] = (short)(2+i*6+7);
            	indices[num++] = (short)(2+i*6+1);
            	indices[num++] = 1;
       	 	}
            break;               
        case 8: //walec prosty
        case 9: //walec pochyly
        	ilosc = 720;

            if (spinner1.getSelectedItemPosition()==9) {
                GoraX = 0.2f;
                GoraY = 0.4f;            
            }
        	
        	promien=0.4f;
        	if (spinner2.getSelectedItemPosition()==1) {
        		promien = 0.8f;
        	}
            vertices = new float[(ilosc+1)*18+6];
                        
        	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
       	 	vertices[num++] = 0.0f;
        	
        	vertices[num++] = 0.0f+GoraX;
       	 	vertices[num++] = 0.0f+GoraY;
       	 	vertices[num++] = 1f;

       	 	for (i=0;i<=ilosc;i++) {
       	 		//na dol
       	 		vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
           	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	
           	 	//na gore
            	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX;
        	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
        	 	vertices[num++] = 1f;
        	 	
        	 	//na boki
            	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue() ;
           	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*i)).floatValue()+GoraX;
        	 	vertices[num++] = promien *((Number)Math.cos(2*Math.PI/ilosc*i)).floatValue()+GoraY;
        	 	vertices[num++] = 1f;        	 	
        	
        	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue() ;
           	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue();
           	 	vertices[num++] = 0.0f;
           	 	vertices[num++] = 0.4f* ((Number)Math.sin(2*Math.PI/ilosc*(i+1))).floatValue()+GoraX;
        	 	vertices[num++] = promien* ((Number)Math.cos(2*Math.PI/ilosc*(i+1))).floatValue()+GoraY;
        	 	vertices[num++] = 1f;
            }


            color = new float[((ilosc+1)*6+2)*4];

            num =0;

            color[num++] = 1f;
        	color[num++] = 0f;
        	color[num++] = 0f;
        	color[num++] = 1f; 
            

            color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
        	color[num++] = 1f;
        	
        	for (i=0;i<=360;i++) {
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 

            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f; 

            	for (int j=0;j<4;j++) {
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f/(ilosc+5)*(i+2);
                	color[num++] = 1f; 
            	}
            }

        	for (i=0;i<360;i++) {
            	color[num++] = 1f;
            	color[num++] = 0f;
            	color[num++] = 0f;
            	color[num++] = 1f; 

            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f;
            	color[num++] = 1f; 

            	for (int j=0;j<4;j++) {
                	color[num++] = 1f/(ilosc+5)*(360-i+2);
                	color[num++] = 1f/(ilosc+5)*(360-i+2);
                	color[num++] = 1f/(ilosc+5)*(360-i+2);
                	color[num++] = 1f;             		
            	}

            }
        	
       	 	indices = new short[(ilosc)*12];

       	 	num=0;

      		for (i=0;i<ilosc;i++) {
      			//1 trojkat boku
      			indices[num++] = (short) (2+i*6+3);
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] = (short) (2+i*6+2);
        	
        		//2 trojkat boku
        		indices[num++] = (short) (2+i*6+5);
        		indices[num++] =(short) (2+i*6+4);
        		indices[num++] =(short) (2+i*6+2);
      		}
      	 	for (i=0;i<ilosc;i++) {
            	//dolna podstawa
            	indices[num++] = (short) (2+i*6+6);
            	indices[num++] = (short) (2+i*6+0);
            	indices[num++] = 0;
       	 	}
      		for (i=0;i<ilosc;i++) {
            	//gorna podstawa
            	indices[num++] = (short)(2+i*6+7);
            	indices[num++] = (short)(2+i*6+1);
            	indices[num++] = 1;
       	 	}
            break;            
        default:
        	break;
        }    	

        // The vertex buffer.
        bb = ByteBuffer.allocateDirect(vertices.length*4 );//byteperfloat=4
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        
        // Setup index-array buffer. Indices in byte.
        ibb = ByteBuffer.allocateDirect(indices.length*2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();            
        indexBuffer.put(indices);
        indexBuffer.position(0);
        
        ByteBuffer cb = ByteBuffer.allocateDirect(color.length* 4);
        cb.order(ByteOrder.nativeOrder());
        colorBuffer = cb.asFloatBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);        
    }
    
	public MyGLRenderer(Activity c, Spinner sp1, Spinner sp2, Spinner sp3, CheckBox c1) {
        myActivity = c;
        spinner1 = sp1;
        spinner2 = sp2;
        spinner3 = sp3;
        checkbox1 = c1;
        
    }
    
    @Override
    public void onDrawFrame(GL10 gl) {
        int mPositionHandle, mColorHandle, mMVPMatrixHandle;

        // clear Screen and Depth Buffer, we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        
        // Set our shader programm
        if (spinner3.getSelectedItemPosition()==0) {  
        	GLES20.glUseProgram(mProgram);
        } else {  
            // Enable depth test
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            // Accept fragment if it closer to the camera than the former one
            GLES20.glDepthFunc(GLES20.GL_LESS);
        	GLES20.glUseProgram(mProgram2);
        }
    
        Matrix.setIdentityM(mModelMatrix, 0);
        
	    Matrix.rotateM(mModelMatrix, 0, xAngle, 1, 0, 0);
	    Matrix.rotateM(mModelMatrix, 0, yAngle, 0, 0, 1);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
     
        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        if (spinner3.getSelectedItemPosition()==0) {
        	// 	get handle to vertex shader's vPosition member
        	mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        } else {
        	// 	get handle to vertex shader's vPosition member
        	mPositionHandle = GLES20.glGetAttribLocation(mProgram2, "vPosition");
        }

        // 	Enable a handle to the triangle vertices
    	GLES20.glEnableVertexAttribArray(mPositionHandle);

    	// 	Prepare the triangle coordinate data
    	GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        
        // Set color for drawing the triangle
        if (spinner3.getSelectedItemPosition()==0) {
            color = new float[4];
            color[0] = 0.63671875f;
            color[1] = 0.76953125f;
            color[2] = 0.22265625f;
            color[3] = 1.0f;
            
            // get handle to fragment shader's vColor member
            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
            
            GLES20.glUniform4fv(mColorHandle, 1, color, 0);
          
            // 	get handle to shape's transformation matrix            
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");         
            //MyGLRenderer.checkGlError("glGetUniformLocation");
        } else {
            // get handle to vertex shader's vPosition member
            mColorHandle = GLES20.glGetAttribLocation(mProgram2, "a_Color");
            // Enable a handle to the triangle vertices
            GLES20.glEnableVertexAttribArray(mColorHandle);

            GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 0, colorBuffer);
        
            // get handle to shape's transformation matrix
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram2, "uMVPMatrix");         
            //MyGLRenderer.checkGlError("glGetUniformLocation");            
        }
        
        
        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        //MyGLRenderer.checkGlError("glUniformMatrix4fv");
         
        if (spinner3.getSelectedItemPosition()==0) {
            switch (spinner1.getSelectedItemPosition()) {
            case 0: //graniastos³up prawid³owy
            case 1: //graniastos³up pochy³y
           	 	// Draw the triangle
            	GLES20.glDrawArrays(GLES20.GL_LINES, 0, (spinner2.getSelectedItemPosition()+3)*6);
           	 	break;
            case 2: //ostros³up prawid³owy
            case 3: //ostros³up pochy³y
            	// Draw the triangle
           	 	if (!checkbox1.isChecked()) { 
           	 		GLES20.glDrawArrays(GLES20.GL_LINES, 0, (spinner2.getSelectedItemPosition()+3)*4);
           	 	} else {
           	 		GLES20.glDrawArrays(GLES20.GL_LINES, 0, (spinner2.getSelectedItemPosition()+3)*6);
           	 	}
           	 	break;
            case 4: //prostopad³oœciañ
            	// Draw the triangle
           	 	GLES20.glDrawArrays(GLES20.GL_LINES, 0, 4*6);
           	 	break;
            case 5: //stozek prosty
            case 6: //stozek pochy³y
            	// Draw the triangle
           	 	if (!checkbox1.isChecked()) {
           	 		
           	 		GLES20.glDrawArrays(GLES20.GL_LINES, 0, (720+20)*2);           		 
           	 	} else {
           	 		GLES20.glDrawArrays(GLES20.GL_LINES, 0, (720+720+20)*2);           		 
           	 	}
           	 	break;
            case 7: //szescian
            	// Draw the triangle
           	 	GLES20.glDrawArrays(GLES20.GL_LINES, 0, 4*6);
           	 	break;
            case 8: //walec prosty
            case 9: //walec pochyly
            	// Draw the triangle
           	 	GLES20.glDrawArrays(GLES20.GL_LINES, 0, (720*2+20)*2);
           	 	break;
            default:
           	 	break;
            }
        	
        } else {
            switch (spinner1.getSelectedItemPosition()) {
            case 0: //graniastos³up prawid³owy
            case 1: //graniastos³up pochy³y
           	 	// Draw the triangle
            	GLES20.glDrawElements(GLES20.GL_TRIANGLES, (spinner2.getSelectedItemPosition()+3)*12, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
            	break;
            case 2: //ostros³up prawid³owy
            case 3: //ostros³up pochy³y
            	// Draw the triangle
           	 	if (!checkbox1.isChecked()) { 
           	 		GLES20.glDrawElements(GLES20.GL_TRIANGLES, (spinner2.getSelectedItemPosition()+3)*6, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	} else {
           	 		GLES20.glDrawElements(GLES20.GL_TRIANGLES, (spinner2.getSelectedItemPosition()+3)*12, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	}
           	 	break;
            case 4: //prostopad³oœciañ
            	// Draw the triangle
            	GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6*6, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	break;
            case 5: //stozek prosty
            case 6: //stozek pochy³y
            	// Draw the triangle
           	 	if (!checkbox1.isChecked()) {
           	 		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 720*6, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	} else {
           	 		GLES20.glDrawElements(GLES20.GL_TRIANGLES, 720*12, GLES20.GL_UNSIGNED_SHORT, indexBuffer);           		 
           	 	}
           	 	break;
            case 7: //szescian
            	// Draw the triangle
            	GLES20.glDrawElements(GLES20.GL_TRIANGLES, (4)*12, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	break;
            case 8: //walec prosty
            case 9: //walec pochyly
            	// Draw the triangle
            	GLES20.glDrawElements(GLES20.GL_TRIANGLES, 720*12, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
           	 	break;
            default:
           	 	break;
            }
        	
        }

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        
        if (spinner3.getSelectedItemPosition()!=0) {        
        	GLES20.glDisableVertexAttribArray(mColorHandle);
        }
        
   //----end draw        
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1,1, 3, 7); 
    }

    public static int loadShader(int type, String shaderCode){
    	 
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
 
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
 
        // return the shader
        return shader;
    }
   
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {    	  
        // Set the clear color to black
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1);
 
        // Create the shaders
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexProgram);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentProgram);
 
        mProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // creates OpenGL ES program executables

        vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexProgram2);
        fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentProgram2);
 
        mProgram2 = GLES20.glCreateProgram();             // create empty OpenGL ES Program
        GLES20.glAttachShader(mProgram2, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram2, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram2);                  // creates OpenGL ES program executables
        
        Matrix.setLookAtM(mViewMatrix, 0, 
        		2.5f, 2.5f, -3f, 
        		0f, 0f, 0f, 
        		0.0f, 1.0f, 0.0f);
        
        ChangeFigura();
    }
}
