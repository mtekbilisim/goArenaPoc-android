package com.mtek.goarenopoc.module.textinput;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.binaryfork.spanny.Spanny;
import com.mtek.goarenopoc.R;
import com.rengwuxian.materialedittext.MaterialEditText;


public class TextInputView extends LinearLayout {

    private Context context;
    private String title;
    private String length;
    private String retailerFormID;
    private int type;
    private String digits;
    private TextWatcher mTextWatcher;
    private TextWatcher mOriginalTextWatcher;
    private boolean isFocusable;
    private boolean isForced;
    private boolean isLightTheme;
    private boolean textAllCaps;
    private String hint;


    TextView txtTitle, txtForced;
    public MaterialEditText txtInput;

    View viewLine;
    RelativeLayout layoutInputs;
    LinearLayout editLayout;

    public TextInputView(Context context) {
        this(context, null);
        init(context, null);
    }

    public TextInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(getContext(), R.layout.view_text_input, this);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtForced = view.findViewById(R.id.txtForced);
        txtInput = view.findViewById(R.id.txtInput);
        editLayout = view.findViewById(R.id.editLayout);

        viewLine = view.findViewById(R.id.viewLine);
        layoutInputs = view.findViewById(R.id.layoutInputs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextInputView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        init(context, attrs);
    }


    public void init(final Context context, AttributeSet attrs) {
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextInputView);
        try {
            title = typedArray.getString(R.styleable.TextInputView_inputTitle);
            hint = typedArray.getString(R.styleable.TextInputView_inputHint);
            length = typedArray.getString(R.styleable.TextInputView_maxLength);
            type = typedArray.getInt(R.styleable.TextInputView_inputType, -1);
            digits = typedArray.getString(R.styleable.TextInputView_inputDigits);
            isFocusable = typedArray.getBoolean(R.styleable.TextInputView_isFocusable, true);
            isForced = typedArray.getBoolean(R.styleable.TextInputView_isForced, false);
            isLightTheme = typedArray.getBoolean(R.styleable.TextInputView_tiv_isWhiteTheme, false);
            textAllCaps = typedArray.getBoolean(R.styleable.TextInputView_textAllCaps, false);
        } finally {
            typedArray.recycle();
        }
        if (isForced) {
            txtForced.setVisibility(View.VISIBLE);
        } else {
            txtForced.setVisibility(View.GONE);
        }
        txtTitle.setText(title);
        txtInput.setText("");
        txtInput.setHint(hint);


        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        layoutInputs.setLayoutParams(lp);

        final boolean[] isDelete = {false};


        txtInput.addTextChangedListener(mOriginalTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0) {
                    isDelete[0] = false;
                } else {
                    isDelete[0] = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = txtInput.getText().toString();
                if (!str.equals("")) {
                    txtTitle.setVisibility(VISIBLE);
                }
                if (type == 3) {
                    if (txtInput.isFocusable()) {
                        try {
                            if (s.toString().indexOf("5") == 0) {

                            } else {
                                if (!isDelete[0]) {
                                    if (!s.toString().equals("")) {
                                        Toast.makeText(context, "Telefon numarası 5 ile başlamalıdır", Toast.LENGTH_SHORT).show();
                                        txtInput.removeTextChangedListener(mOriginalTextWatcher);
                                        txtInput.setText("");
                                        txtInput.addTextChangedListener(mOriginalTextWatcher);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textAllCaps) {
                    setAllCaps(s.toString(), txtInput);
                }
            }
        });

        setMaxLength(length);

        if (type == 0) {
            txtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (type == 1) {
            txtInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (type == 2) {
            txtInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (type == 3) {
            txtInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (type == 4) {
            txtInput.setFilters(new InputFilter[]{getOnlyCharactersFilter()});
            txtInput.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        } else if (type == 5) {
            txtInput.setInputType(0x00000081);
        }

        if (digits != null && !digits.equals("") && digits.equals("0123456789")) {
            txtInput.setKeyListener(DigitsKeyListener.getInstance(digits));
        }

        if (!isFocusable) {
            txtInput.setFocusable(false);
            txtInput.setFocusableInTouchMode(false);
            txtInput.setVisibility(VISIBLE);
        }
        if (isLightTheme) {
            txtInput.setTextColor(ContextCompat.getColor(context, R.color.white));
            txtInput.setMetHintTextColor(ContextCompat.getColor(context, R.color.white));
            txtTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    public void setAllCaps(String arg, MaterialEditText txtNumber) {
        if (!arg.equals(arg.toUpperCase())) {
            arg = arg.toUpperCase();
            txtNumber.setText(arg);
            txtNumber.setSelection(txtNumber.getText().length());
        }
    }

    public void setMaxLength(String length) {
        if (length != null && !length.equals("")) {
            int maxLength = Integer.parseInt(length);
            InputFilter[] fArray = new InputFilter[1];
            fArray[0] = new InputFilter.LengthFilter(maxLength);
            txtInput.setFilters(fArray);
        }
    }

    public static InputFilter getOnlyCharactersFilter() {
        return getCustomInputFilter(true, false, false);
    }

    public static InputFilter getCharactersAndDigitsFilter() {
        return getCustomInputFilter(true, true, false);
    }

    public static InputFilter getCustomInputFilter(final boolean allowCharacters, final boolean allowDigits, final boolean allowSpaceChar) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                boolean keepOriginal = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) {
                        sb.append(c);
                    } else {
                        keepOriginal = false;
                    }
                }
                if (keepOriginal) {
                    return null;
                } else {
                    if (source instanceof Spanned) {
                        SpannableString sp = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                        return sp;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                if (Character.isLetter(c) && allowCharacters) {
                    return true;
                }
                if (Character.isDigit(c) && allowDigits) {
                    return true;
                }
                if (Character.isSpaceChar(c) && allowSpaceChar) {
                    return true;
                }
                //  if (Character.isLetter())
                return false;
            }
        };
    }

    public void setType(int type, String digits, int length) {
        this.type = type;
        this.digits = digits;
        this.length = String.valueOf(length);

        if (type == 0) {
            txtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (type == 1) {
            txtInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (type == 2) {
            txtInput.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else if (type == 3) {
            //txtInput.setInputType(InputType.TYPE_CLASS_PHONE);
            //txtInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
            txtInput.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else if (type == 4) {
            txtInput.setFilters(new InputFilter[]{getOnlyCharactersFilter()});
            txtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }

        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(length);
        txtInput.setFilters(fArray);

        txtInput.setKeyListener(DigitsKeyListener.getInstance(digits));

    }


    public void cleanText() {
        if (mTextWatcher != null) {
            txtInput.removeTextChangedListener(mTextWatcher);
        }
        txtInput.setText("");

        setDefault();
        setTextWatcher();

    }

    public void notFocusableClear() {
        if (mTextWatcher != null) {
            txtInput.removeTextChangedListener(mTextWatcher);
        }
        txtInput.setText("");
        setDefault();
        setTextWatcher();
        //txtInput.setHint(title);
    }

    public void setValidation() {
        setError();
        setTextWatcher();
    }

    public void setTextWatcher() {
        //setFocus(txtEditText);

        txtInput.addTextChangedListener(mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = txtInput.getText().toString();

                if (str.length() == 0) {
                    //layoutEditText.setBackgroundResource(R.drawable.bg_rectangle_light_red_solid_red_stroke);
                    viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_error));
                } else {
                    //layoutEditText.setBackgroundResource(R.drawable.bg_rectangle_white_solid);
                    viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setDefault() {
        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
    }

    public void setError() {
        viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_error));
    }

    public void setText(String str) {
        if (str == null) {
            str = "";
        }
        txtInput.setText(str);

    }


    public void setTitle(String str) {
        txtInput.setHint(str);
    }


    public void setTextAlignmentCenter() {
        txtInput.setTextAlignment(TEXT_ALIGNMENT_CENTER);
    }

    public void setTitle(boolean isForced, String title) {
        if (title == null) {
            title = "";
        }

        this.title = title;

        Spanny spanTitle = new Spanny();
        if (isForced) {
            spanTitle.append("* ", new ForegroundColorSpan(Color.RED));
        }
        spanTitle.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_title)));

        Spanny spanHint = new Spanny();
        if (isForced) {
            spanHint.append("* ", new ForegroundColorSpan(Color.RED));
        }
        spanHint.append(title, new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_form_view_hint)));

        txtTitle.setText(spanTitle);
        txtInput.setHint(spanHint);
    }


    public String getText() {
        return txtInput.getText().toString();
    }

    public int getType() {
        return type;
    }

    public String getHint() {
        return txtInput.getHint().toString();
    }

    public String getTitle() {
        return txtTitle.getText().toString();
    }

    public void setEnable(boolean status) {
        txtInput.setFocusable(status);
        txtInput.setFocusableInTouchMode(status);
    }

    public String getRetailerFormID() {
        return retailerFormID;
    }

    public void setRetailerFormID(String retailerFormID) {
        this.retailerFormID = retailerFormID;
    }

    public void setFocusable(boolean b) {
        setEnable(b);
        if (!b) {
            txtInput.setMetHintTextColor(ContextCompat.getColor(context, R.color.color_view_line_default));
        }

    }

    public boolean check() {
        String s = txtInput.getText().toString();
        setTextWatcher();
        if (s.trim().equals("")) {
            //layoutExplanation.setBackgroundResource(R.drawable.bg_rectangle_light_red_solid_red_stroke);
            viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_error));
            return false;
        } else {
            //layoutExplanation.setBackgroundResource(R.drawable.bg_rectangle_white_solid_gray_stroke);
            viewLine.setBackgroundColor(ContextCompat.getColor(context, R.color.color_view_line_default));
            return true;
        }

    }

    public void setEditBackgroundColor() {
        editLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

    }

}
