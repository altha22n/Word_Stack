/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.wordstack;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private static final int WORD_LENGTH = 5;
    public static final int LIGHT_BLUE = Color.rgb(176, 200, 255);
    public static final int LIGHT_GREEN = Color.rgb(200, 255, 200);
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();
    private StackedLayout stackedLayout;
    private String word1, word2;
   // private ArrayList<String> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // wordList = new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = in.readLine()) != null) {
                String word = line.trim();
                /**
                 **
                 **  DONE
                 **
                 **/
                if (word.length() <= WORD_LENGTH){
                    words.add(word);

                }

            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        LinearLayout verticalLayout = (LinearLayout) findViewById(R.id.vertical_layout);
        stackedLayout = new StackedLayout(this);
        verticalLayout.addView(stackedLayout, 3);

        View word1LinearLayout = findViewById(R.id.word1);
        word1LinearLayout.setOnTouchListener(new TouchListener());
        //word1LinearLayout.setOnDragListener(new DragListener());
        View word2LinearLayout = findViewById(R.id.word2);
        word2LinearLayout.setOnTouchListener(new TouchListener());
        //word2LinearLayout.setOnDragListener(new DragListener());
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                LetterTile tile = (LetterTile) stackedLayout.peek();
                tile.moveToViewGroup((ViewGroup) v);
                if (stackedLayout.empty()) {

                    TextView messageBox = (TextView) findViewById(R.id.message_box);
                    messageBox.setText(word1 + " " + word2);
                }
                /**
                 **
                 **  YOUR CODE GOES HERE
                 **
                 **/
                return true;
            }
            return false;
        }
    }

    private class DragListener implements View.OnDragListener {

        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(LIGHT_GREEN);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(LIGHT_BLUE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(Color.WHITE);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign Tile to the target Layout
                    LetterTile tile = (LetterTile) event.getLocalState();
                    tile.moveToViewGroup((ViewGroup) v);
                    if (stackedLayout.empty()) {
                        TextView messageBox = (TextView) findViewById(R.id.message_box);
                        messageBox.setText(word1 + " " + word2);
                    }
                    /**
                     **
                     **  YOUR CODE GOES HERE
                     **
                     **/
                    return true;
            }
            return false;
        }
    }

    public boolean onStartGame(View view) {
        TextView messageBox = (TextView) findViewById(R.id.message_box);
        messageBox.setText("Game started");
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/

      //  Random random = new Random();


        // randomly selects an index from the arr
        int first = random.nextInt(words.size());
        int second = random.nextInt(words.size());

        //this randomly selects words from the dictionary
        word1 = words.get(first);
        word2 = words.get(second);

        Log.d("wo1","word1: " + word1);
        Log.d("wo2","word2: " + word2);




        //converting the two words to char arrays
        char[] wo1 = word1.toCharArray();
        char[] wo2 = word2.toCharArray();


        String test1 = new String(wo1);
        String test2 = new String (wo2);

        Log.d("wo1","wo1: " + test1);
        Log.d("wo2","wo2: " + test2);

        //our final scrambles string
        String scrambled= new String();

        //counters for the two words
        int w1 = 0;
        int w2 = 0;

        int which_word;


//        int w1 = random.nextInt(5);
//        int w2 = random.nextInt(5);
        //char c;

        while (w1 < wo1.length || w2 < wo2.length) {

            which_word = random.nextInt(2);
            //Log.d("whichword","which-word? " + which_word);

            if (which_word == 0 && w1 <= wo1.length) {
                Log.d("test","first if");
                char c = wo1[w1];


                scrambled+=(Character.toString(c));
               // str.append(c);
                Log.d("scram1", "scram is: " + scrambled);
                w1++;

                //////
                //push takes in a View object
//                Context con = scrambled;
                LetterTile tile = new LetterTile(con,c);
//
//                        (LetterTile) stackedLayout.peek();
//                tile.moveToViewGroup((ViewGroup) view );
//                Log.d("tile","tile?" + tile);

                /////

                continue;

            }  if (which_word == 1 && w2 <= wo2.length) {
                Log.d("test","second if");

                char c = wo2[w2];


                scrambled+=(Character.toString(c));
                //str.append(c);
                Log.d("scram2", "scram is: " + scrambled);
                w2++;
                continue;

            }

            else if (w2 > wo2.length || w1 > wo1.length){

                break;
            }



        }
        messageBox.setText(scrambled);
        Log.d("messageBox", "scrambled: " + scrambled);


        return true;
    }

    public boolean onUndo(View view) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        return true;
    }
}
