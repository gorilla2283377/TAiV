import java.io.*;

public class nka {

    // Алфавит
    static final char[] ALPHABET = {'0', '1'};

    // Индексы состояний: q_ij -> i*2 + j
    static final int Q00 = 0, Q01 = 1,
                     Q10 = 2, Q11 = 3,
                     Q20 = 4, Q21 = 5,
                     Q30 = 6, Q31 = 7,
                     Q40 = 8, Q41 = 9;

    // Табличное представление автомата
    static final int[][] TRANSITION = {
        //   0     1
        { Q10, Q01 }, // Q00
        { Q11, Q00 }, // Q01
        { Q20, Q11 }, // Q10
        { Q21, Q10 }, // Q11
        { Q30, Q21 }, // Q20
        { Q31, Q20 }, // Q21
        { Q40, Q31 }, // Q30
        { Q41, Q30 }, // Q31
        { Q00, Q41 }, // Q40
        { Q01, Q40 }  // Q41
    };

    static final int START_STATE = Q00;
    static final int[] FINAL_STATES = { Q00 };

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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter string over {0,1} (empty line to stop):");

        while (true) {
            System.out.print("> ");
            int first = br.read();
            if (first == -1) break;

            // Пустая строка: 0 нулей mod 5 = 0, 0 единиц mod 2 = 0 -> Accept
            if (first == '\n' || first == '\r') {
                System.out.println("(empty) -> Accept");
                continue;
            }

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