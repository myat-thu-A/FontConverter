package it.myatthu.fontconverter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.myatthu.fontconverter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivityMainBinding binding;

    private Typeface zawgyi, unicode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListeners();
        initTypeface();
    }

    private void initTypeface() {
        zawgyi = ResourcesCompat.getFont(this, R.font.zawgyi);
        unicode = ResourcesCompat.getFont(this, R.font.unicode);
    }

   /* private void showSample() {
        String input = "abc";
        String output = input.replaceAll("[aA]", "\u1000");
        output = input.replaceAll("[bB]", "\u1001");
        output = input.replaceAll("[cC]", "\u1002");
        Log.d("Myat", output);
    }*/

    private void initListeners() {
        binding.rgFonts.setOnCheckedChangeListener(this);
        binding.btConvert.setOnClickListener(v -> {
            String input = binding.etInput.getText().toString();
            if(binding.rbUni2Zg.isChecked()) {
                //Convert Unicode to Zawgyi
                String converted = RabbitConverter.uni2zg(input);
                binding.etOutput.setText(converted);
            } else {
                //Zawgyi to Unicode
                String converted = RabbitConverter.zg2uni(input);
                binding.etOutput.setText(converted);
            }
        });

        binding.btClear.setOnClickListener(v -> {
            binding.etInput.getText().clear();
            binding.etOutput.getText().clear();

        });
        //showSample();

        binding.btCopy.setOnClickListener(v -> {
            if(!binding.etOutput.getText().toString().isEmpty()) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("font_converter", binding.etOutput.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(this, "Output text copied", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No output text to copy", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rbUni2Zg) {
            //Unidode to Zawgyi
            binding.etInput.setTypeface(unicode);
            binding.etOutput.setTypeface(zawgyi);
            Log.d("Myat", "Unicode to Zawgyi");
        } else {
            //Zawgyi to Unicode
            Log.d("Myat", "Zawgyi to Unicode");
            binding.etInput.setTypeface(zawgyi);
            binding.etOutput.setTypeface(unicode);
        }
    }
}