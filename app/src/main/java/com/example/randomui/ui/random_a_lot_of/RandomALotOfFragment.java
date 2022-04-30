package com.example.randomui.ui.random_a_lot_of;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.randomui.R;

import java.util.ArrayList;
import java.util.Random;

public class RandomALotOfFragment extends Fragment implements View.OnClickListener {

    private View root;

    private Random random = new Random();

    private Button Random_btn;
    private EditText etMIN;
    private EditText etMAX;
    private EditText etTIMES;

    int times;
    protected ArrayList<Integer> array_nums = new ArrayList<Integer>(0);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.w("MyLogs", "RandomALotOfFragment: onCreateView");
        root = inflater.inflate(R.layout.fragment_random_a_lot_of, container, false);
        Random_btn = root.findViewById(R.id.RandomALotOf_btn);
        Random_btn.setOnClickListener(this);
        etMIN = root.findViewById(R.id.RandomALotOf_etMIN);
        etMAX = root.findViewById(R.id.RandomALotOf_etMAX);
        etTIMES = root.findViewById(R.id.RandomALotOf_etTIMES);


        return root;
    }

    @Override
    public void onClick(final View view) { //Метод для нажатия на кнопку btn1. Инициализация обьекта как final требуется для избежания ошибок. НЕ УДАЛЯТЬ БЕЗ УВЕРЕНОСТИ!
        switch (view.getId()) {
            case R.id.RandomALotOf_btn:
                getNums();
                //Тут происходит обьявления нового интента, вкладывание в него ArrayList'а и переход на другое активити.
                break;
        }
    }

    private void getNums() { //Функция получает случайные числа и передаёт эти значения в ArrayList<Integer>
        int min;
        int max;
        int diff;
        int num;
        String nums = ""; //Эта строка нужна для вывода в лог всех чисел.
        //Необходимые для работы программы переменные.
        //Присваивание значений двум переменным.
        try {
            min = Integer.parseInt(String.valueOf(etMIN.getText()));
            max = Integer.parseInt(String.valueOf(etMAX.getText()));
            times = Integer.parseInt(String.valueOf(etTIMES.getText()));
            for (int i = 0; i < times; i++) {
                diff = max - min;
                num = random.nextInt(diff + 1);
                num += min;
                nums += num + " ";
                array_nums.add(num);

            }
            Intent intent = new Intent(getActivity(), RandomALotOf_listView.class);
            intent.putIntegerArrayListExtra("array_nums", array_nums);
            startActivity(intent);
            Log.w("MyLogs", "RandomFragment.onClick: arrayList = " + nums);
            array_nums.clear(); //Это выражение нужно для очистки массива после его использование. Без этой строки в listView будет показываться все полученные числа за каждый нажатый на кнопку раз. И поэтому я добавил эту строку, что бы при каждом нажатии показывались новые числа.


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
            for (int i = 0; i < times; i++) {
                diff = max - min;
                num = random.nextInt(diff + 1);
                num += min;
                nums += num + " ";
                array_nums.add(num);
            }
            Intent intent = new Intent(getActivity(), RandomALotOf_listView.class);
            intent.putIntegerArrayListExtra("array_nums", array_nums);
            startActivity(intent);
            Log.w("MyLogs", "RandomFragment.onClick: arrayList = " + nums);
        }
    }
}