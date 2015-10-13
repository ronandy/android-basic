package com.example.andy.calculatorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtResult = (TextView) findViewById(R.id.txt_result);
    }

    public void doClick(View view){
        if(txtResult.getText().equals("0")){
            txtResult.setText("");
        }
        switch (view.getId()){
            case R.id.btn_zero:
            case R.id.btn_n1:
            case R.id.btn_n2:
            case R.id.btn_n3:
            case R.id.btn_n4:
            case R.id.btn_n5:
            case R.id.btn_n6:
            case R.id.btn_n7:
            case R.id.btn_n8:
            case R.id.btn_n9:
                Button button = (Button) view;
                txtResult.setText(txtResult.getText() + button.getText().toString());
                break;
            case R.id.btn_suma:
                txtResult.setText(txtResult.getText() + "+");
                break;
            case R.id.btn_resta:
                txtResult.setText(txtResult.getText() + "-");
                break;
            case R.id.btn_multi:
                txtResult.setText(txtResult.getText() + "*");
                break;
            case R.id.btn_divide:
                txtResult.setText(txtResult.getText() + "/");
                break;
            case R.id.btn_equals:
                //System.out.println(eval("2^3 - 3 + 1 + 3 * ((4+4*4)/2) / 5 + -5"));
                //txtResult.setText(""+eval(txtResult.getText().toString()));
                txtResult.setText(decimales(eval(txtResult.getText().toString())));
                break;
            case R.id.btn_delete:
                txtResult.setText(backspace(txtResult.getText().toString()));
                break;
        }
    }

    public static String decimales(Double res){
        double resultado = res;
        int p_ent = (int)resultado;
        double p_dec = resultado - p_ent;
        if(p_dec>0) {
            return ""+resultado;
        }
        else {
            return ""+p_ent;
        }
    }

    public static String backspace(String text){
        while (text.length()>0) {
            return text.substring(0, text.length() - 1);
        }
        return text;
    }


    public static double eval(final String str) {
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term brackets
            // factor = brackets | number | factor `^` factor
            // brackets = `(` expression `)`

            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') { // multiplication
                        if (c == '*') eatChar();
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') { // brackets
                    eatChar();
                    v = parseExpression();
                    if (c == ')') eatChar();
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    while ((c >= '0' && c <= '9') || c == '.') {
                        sb.append((char)c);
                        eatChar();
                    }
                    if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                    v = Double.parseDouble(sb.toString());
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
                return v;
            }
        }
        return new Parser().parse();
    }
}
