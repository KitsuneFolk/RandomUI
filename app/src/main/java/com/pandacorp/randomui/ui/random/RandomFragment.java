package com.pandacorp.randomui.ui.random;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pandacorp.randomui.R;

import java.util.Random;

public class RandomFragment extends Fragment implements View.OnClickListener {

    private View root;

    private Random random = new Random();

    private Button Random_btn;
    private TextView Random_text_tv;
    private EditText etMIN;
    private EditText etMAX;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.w("MyLogs", "RandomFragment: onCreateView");
        root = inflater.inflate(R.layout.fragment_random, container, false);
        Random_btn = root.findViewById(R.id.Random_btn);
        Random_btn.setOnClickListener(this);
        Random_text_tv = (TextView) root.findViewById(R.id.Random_text_tv);
        etMIN = (EditText) root.findViewById(R.id.etMIN);
        etMAX = (EditText) root.findViewById(R.id.etMAX);

        return root;
    }

    @Override
    public void onClick(final View view) { //Метод для нажатия на кнопку btn1. Инициализация обьекта как final требуется для избежания ошибок. НЕ УДАЛЯТЬ БЕЗ УВЕРЕНОСТИ!
        if (view.getId() == R.id.Random_btn) {
            Log.w("MyLogs", "RandomFragment.onClick: Random_btn is clicked");
            getNum();
        }
    }

    private void getNum() { //Функция получает случайное число и присваивает его tv1.

        int min;
        int max;
        int diff;
        int num; //Необходимые для работы программы переменные.
        //Присваивание значений двум переменным.
        try {
            min = Integer.parseInt(String.valueOf(etMIN.getText()));
            max = Integer.parseInt(String.valueOf(etMAX.getText()));
            diff = max - min;
            num = random.nextInt(diff + 1);
            num += min;
            Log.w("MyLogs", "RandomFragment.getNum: Random int is " + num);
            Random_text_tv.setText(String.valueOf(num));
        } catch (NumberFormatException ise) { //Обработка нажатия на кнопку без ввода цифр.
            //pass;
        } catch (IllegalArgumentException il) { //Обработка ввода второго аргумента меньше первого.
            int temp;
            min = Integer.parseInt(String.valueOf(etMIN.getText()));
            max = Integer.parseInt(String.valueOf(etMAX.getText()));
            temp = min;
            min = max;
            max = temp;
            diff = max - min;
            num = random.nextInt(diff + 1);
            num += min;
            Log.w("MyLogs", "RandomFragment.getNum: Random int is " + num);
            Random_text_tv.setText(String.valueOf(num));
        }
    }
}