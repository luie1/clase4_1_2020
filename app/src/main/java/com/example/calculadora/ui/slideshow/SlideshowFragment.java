package com.example.calculadora.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculadora.R;

import java.util.Stack;

public class SlideshowFragment extends Fragment implements View.OnClickListener{

    TextView pantalla;
    Button uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,cero,suma,resta,mult,div,elev,ap,close,res,reset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        pantalla=root.findViewById(R.id.pantalla);
        uno=root.findViewById(R.id.uno);
        dos=root.findViewById(R.id.dos);
        tres=root.findViewById(R.id.tres);
        cuatro=root.findViewById(R.id.cuatro);
        cinco=root.findViewById(R.id.cinco);
        seis=root.findViewById(R.id.seis);
        siete=root.findViewById(R.id.siete);
        ocho=root.findViewById(R.id.ocho);
        nueve=root.findViewById(R.id.nueve);
        cero=root.findViewById(R.id.cero);
        suma=root.findViewById(R.id.suma);
        resta=root.findViewById(R.id.resta);
        mult=root.findViewById(R.id.multiplicacion);
        div=root.findViewById(R.id.division);
        elev=root.findViewById(R.id.elevado);
        ap=root.findViewById(R.id.apertura);
        close=root.findViewById(R.id.cierre);
        reset=root.findViewById(R.id.reset);
        res=root.findViewById(R.id.resultado);


        uno.setOnClickListener(this);
        dos.setOnClickListener(this);
        tres.setOnClickListener(this);
        cuatro.setOnClickListener(this);
        cinco.setOnClickListener(this);
        seis.setOnClickListener(this);
        siete.setOnClickListener(this);
        ocho.setOnClickListener(this);
        nueve.setOnClickListener(this);
        cero.setOnClickListener(this);
        suma.setOnClickListener(this);
        resta.setOnClickListener(this);
        mult.setOnClickListener(this);
        div.setOnClickListener(this);
        elev.setOnClickListener(this);
        ap.setOnClickListener(this);
        close.setOnClickListener(this);
        reset.setOnClickListener(this);
        res.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.uno:pantalla.setText(pantalla.getText().toString()+"1");             break;
            case R.id.dos:pantalla.setText(pantalla.getText().toString()+"2");             break;
            case R.id.tres:pantalla.setText(pantalla.getText().toString()+"3");            break;
            case R.id.cuatro:pantalla.setText(pantalla.getText().toString()+"4");          break;
            case R.id.cinco:pantalla.setText(pantalla.getText().toString()+"5");           break;
            case R.id.seis:pantalla.setText(pantalla.getText().toString()+"6");            break;
            case R.id.siete:pantalla.setText(pantalla.getText().toString()+"7");           break;
            case R.id.ocho:pantalla.setText(pantalla.getText().toString()+"8");            break;
            case R.id.nueve:pantalla.setText(pantalla.getText().toString()+"9");           break;
            case R.id.cero:pantalla.setText(pantalla.getText().toString()+"0");            break;
            case R.id.suma:pantalla.setText(pantalla.getText().toString()+"+");            break;
            case R.id.resta:pantalla.setText(pantalla.getText().toString()+"-");           break;
            case R.id.multiplicacion:pantalla.setText(pantalla.getText().toString()+"*");  break;
            case R.id.division:pantalla.setText(pantalla.getText().toString()+"/");        break;
            case R.id.elevado:pantalla.setText(pantalla.getText().toString()+"^");         break;
            case R.id.apertura:pantalla.setText(pantalla.getText().toString()+"(");        break;
            case R.id.cierre:pantalla.setText(pantalla.getText().toString()+")");          break;
            case R.id.reset:pantalla.setText("");                                          break;
            case R.id.resultado:pantalla.setText(calcular(pantalla.getText().toString()));con=0;break;

        }
    }

    int con=0;
    private String calcular(String cad) {
        Stack<String> ent=new Stack<>();
        Stack<String> signos=new Stack<>();
        String aux="";
        try {
            while(con<cad.length()){
                String x=cad.charAt(con)+"";
                if(isNumber(x)){
                    aux+=x;
                }else{
                    if(!aux.equals("")){
                        ent.push(aux);
                        aux="";
                    }
                    if(x.equals("(")){
                        con++;
                        ent.push(calcular(cad));
                    }else if(x.equals(")")){
                        while(!signos.isEmpty()){
                            ent.push(opera(signos.pop(),ent.pop(),ent.pop()));
                        }
                        return ent.pop();
                    }else if(!signos.isEmpty()){
                        if(priority(x)>priority(ent.peek())){
                            while(!signos.isEmpty()){
                                ent.push(opera(signos.pop(),ent.pop(),ent.pop()));
                            }
                            signos.push(x);
                        }else if(priority(x)==priority(ent.peek())){
                            ent.push(opera(signos.pop(),ent.pop(),ent.pop()));
                            signos.push(x);
                        }else{
                            signos.push(x);
                        }
                    }else{
                        signos.push(x);
                    }
                }
                con++;
            }
            while(!signos.isEmpty()){
                if(!aux.equals(""))
                    ent.push(aux);
                ent.push(opera(signos.pop(),ent.pop(),ent.pop()));
            }
            return ent.pop();
        }catch (Exception e){
            return ""+e;
        }
    }

    private int priority(String x) {
        switch (x){
            case "+":return 2;
            case "-":return 2;
            case "*":return 1;
            case "/":return 1;
            case "^":return 0;
            default:break;
        }
        return -1;
    }

    private String opera(String s, String b, String a) {
        int aa=Integer.parseInt(a);
        int bb=Integer.parseInt(b);
        switch (s){
            case "+":return (aa+bb)+"";
            case "-":return (aa-bb)+"";
            case "*":return (aa*bb)+"";
            case "/":return (aa/bb)+"";
            case "^":return ((int)Math.pow(aa,bb))+"";
            default:break;
        }
        return "";
    }

    private boolean isNumber(String x) {
        try {
            Integer.parseInt(x);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}