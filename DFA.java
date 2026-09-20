import java.io.*;

public class DFA {

    // Алфавит
    static final char[] ALPHABET = {'a', 'b'};

    static final int Q0 = 0, Q1 = 1, Q2 = 2, Q3 = 3, Q4 = 4,
                     Q5 = 5, Q6 = 6, Q7 = 7, Q8 = 8, Q9 = 9,
                     Q10 = 10, Q11 = 11;

    // Табличное представление автомата
    static final int[][] TRANSITION = {
        { Q1,  Q11 },
        { Q11, Q2  },
        { Q11, Q3  },
        { Q11, Q4  },
        { Q11, Q5  },
        { Q6,  Q6  },
        { Q6,  Q7  },
        { Q6,  Q8  },
        { Q6,  Q9  },
        { Q6,  Q10 },
        { Q6,  Q10 },
        { Q11, Q11 }
    };

    static final int START_STATE = Q0;
    static final int[] FINAL_STATES = { Q10 };

    static int symbolIndex(int c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == (char) c) return i;
        }
        return -1;
    }

    static boolean isFinal(int state) {
        for (int f : FINAL_STATES) if (f == state) return true;
        return false;
    }

    // Запуск ДКА: читаем символы из потока, пока не конец строки
    static boolean run(Reader in) throws IOException {
        int state = START_STATE;
        int c;
        while ((c = in.read()) != -1) {
            if (c == '\n' || c == '\r') break;
            int idx = symbolIndex(c);
            if (idx == -1) return false;
            state = TRANSITION[state][idx];
        }
        return isFinal(state);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter string over {a,b} (empty line to stop):");

        while (true) {
            System.out.print("> ");
            int first = br.read();
            if (first == -1) break;

            // Пустая строка -> Reject
            if (first == '\n' || first == '\r') {
                System.out.println("(empty) -> Reject");
                continue;
            }

            // Запускаем ДКА, начиная с уже прочитанного первого символа
            int state = START_STATE;
            int c = first;
            boolean ok = true;

            while (c != -1 && c != '\n' && c != '\r') {
                int idx = symbolIndex(c);
                if (idx == -1) { ok = false; break; }
                state = TRANSITION[state][idx];
                c = br.read();
            }

            if (ok && isFinal(state)) System.out.println("Accept");
            else System.out.println("Reject");
        }
    }
}