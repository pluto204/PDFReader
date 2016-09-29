package pdfreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Automata {
    private ArrayList<int[]> automata;
    public final int ALPHABET_SIZE = 90;

    private int finishState;
    public void initAutomata()
    {
        automata = new ArrayList<int[]>();
        int[] start = new int[ALPHABET_SIZE];
        int[] end = new int[ALPHABET_SIZE];
        //-1 is empty value
        Arrays.fill(start, -1);
        //0 is the last value, meaning the end of the word
        Arrays.fill(end, 0);
        automata.add(start);
        automata.add(end);
        finishState = 1;
    }

    public void addWord(int[] word)
    {
        //end of word is representated by 0
        int stateIndex = 0;
        int wordIndex = 0;
        while(word[wordIndex] != 0)
        {
            if (automata.get(stateIndex)[word[wordIndex]] != -1)
            {
                //this route has already been here, so just follow it
                stateIndex = automata.get(stateIndex)[word[wordIndex]];


            }
            else
            {
                //there is no route here, so lets make it up
                //create a new empty state
                int[] A = new int[ALPHABET_SIZE];
                //add it before the last state
                automata.add(A);
                int newState = automata.size();
                automata.get(stateIndex)[word[wordIndex]] = newState;

                //move on
                stateIndex = newState;
            }
            wordIndex++;
        }
        //end-of-word symbol is linked to the finish state 
        automata.get(stateIndex)[word[wordIndex]] = finishState;

    }

    public boolean recognizeWord(int[] word)
    {
        boolean result = true;
        int wordIndex = 0;
        int stateIndex = 0;
        while (word[wordIndex] != 0) //go till the end of the word
        {
            if (automata.get(stateIndex)[wordIndex] == -1) //can't find the transition
            {
                return false;
            }
            else
            {
                stateIndex = automata.get(stateIndex)[wordIndex];
                wordIndex++;
            }
        }
        if (automata.get(stateIndex)[word[wordIndex]] == finishState)
        {
            //end-of-word symbol reach finish state -> this word is recognized
            return true;
        }
        else
        {
            return false;
        }
    }
    public void saveAutomata()
    {
        try {
            PrintWriter writer = new PrintWriter("dictionary.txt", "UTF-8");
            String line = new String("");
            for(int i = 0; i < automata.size(); i++)
            {
                for (int j = 0; j < ALPHABET_SIZE; j++)
                {
                    line += (automata.get(i)[j]+" ");
                }
                writer.println(line);
                line = "";
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public boolean loadAutomata()
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader("dictionary.txt"));
            String line;
            automata.clear();
            int stateIndex = 0;
            while ((line = br.readLine()) != null)
            {
                if (line.isEmpty()) continue;
                Scanner sc = new Scanner(line);
                int[] transitions = new int[ALPHABET_SIZE];
                for (int i = 0; i < ALPHABET_SIZE; i++)
                {
                    transitions[i] = sc.nextInt();
                }
                automata.add(transitions);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
