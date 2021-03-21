package src;

import java.util.ArrayList;

class ParserException extends Exception {
    String errStr; // 에러 처리
    public ParserException(String str) {
        errStr = str;
    }
    public String toString() {
        return errStr;
    }
}
class Parser {
    // 토큰 타입 상수
    final int NONE = 0;
    final int DELIMITER = 1;
    final int VARIABLE = 2;
    final int NUMBER = 3;
    final int STRING = 4;
    // 신택스 에러 상수
    final int SYNTAX = 0;
    final int UNBALPARENS = 1;
    final int NOEXP = 2;
    final int DIVBYZERO = 3;
    // 표현의 끝을 나타냄
    final String EOE = "\0";
    private String exp; // 표현하는 문자열
    private int expIdx; // 현재 인덱스 값
    private int strIdx = 0;
    private String token; // 현재 토큰 값
    private int tokType; // 토큰값의 변수 타입
    // 변수 배열
    private double vars[] = new double[26];
    private String strvars[] = new String[26];
    private String strvar;

    String s1, s2;
    private boolean IsitString = false;
    // 파서 시작부분
    public String evaluate(String expstr) throws ParserException
    {
        String result;
        exp = expstr;
        expIdx = 0;
        getToken();
        if(token.equals(EOE))
            handleErr(NOEXP); // 표현식 없음

        result = evalExp1();
        if(!token.equals(EOE)) // 마지막 토큰은 EOE여야 한다.
            handleErr(SYNTAX);
        if(IsitString == true)  {
            return strvars[strIdx];
        } else {
            return String.valueOf(result);
        }
    }
    private String evalExp1() throws ParserException
    {
        double result;
        int varIdx;
        int ttokType;
        String temptoken;

        if(tokType == VARIABLE) {
            temptoken = new String(token);
            ttokType = tokType;
            varIdx = Character.toUpperCase(token.charAt(0)) - 'A'; //여길 거치면 varIdx 다시 0됨
            getToken();
            if(!token.equals("=")) {
                putBack();
                token = new String(temptoken);
                tokType = ttokType;
            }
            else {
                getToken();
                result = evalExp2();
                vars[varIdx] = result;
                strvars[strIdx] = strvar;
                return Double.toString(result);
            }
        }
        return Double.toString(evalExp2());
    }
    private double evalExp2() throws ParserException
    {
        char op;
        double result;
        double partialResult;
        result = evalExp3();
        while((op = token.charAt(0)) == '+' || op == '-') {
            getToken();
            partialResult = evalExp3();
            switch(op) {
                case '-':
                    result = result - partialResult;
                    break;
                case '+':
                    if (IsitString = true) {
                        int i = 1;
                        while(strvars[i] != null) {
                            s1 = strvars[i];
                            s2 = strvars[++i];
                            i++;
                        }
                        strIdx++;
                        strvars[strIdx] = s1 + s2;
                        return strIdx;
                    }
                    else {
                        result = result + partialResult; }
                    break;
            }
        }
        return result;
    }
    // 곱하기, 나누기
    private double evalExp3() throws ParserException
    {
        char op;
        double result;
        double partialResult;
        result = evalExp4();
        while((op = token.charAt(0)) == '*' || op == '/' || op == '%') {
            getToken(); //마지막 숫자 4 불러옴
            partialResult = evalExp4();
            switch(op) {
                case '*':
                    result = result * partialResult;
                    break;
                case '/':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result = result / partialResult;
                    break;
                case '%':
                    if(partialResult == 0.0)
                        handleErr(DIVBYZERO);
                    result = result % partialResult;
                    break;
            }
        }
        return result;
    }
    // 지수처리
    private double evalExp4() throws ParserException
    {
        double result;
        double partialResult;
        double ex;
        int t;

        result = evalExp5();

        if(token.equals("^")) {
            getToken();
            partialResult = evalExp4();
            ex = result;
            if(partialResult == 0.0) {
                result = 1.0;
            } else
                for(t=(int)partialResult-1; t > 0; t--)
                    result = result * ex;
        }
        return result;
    }
    // 단항 +,- 처리하기
    private double evalExp5() throws ParserException
    {
        double result;
        String op;

        op = "";
        if((tokType == DELIMITER) && token.equals("+") || token.equals("-")) {
            op = token;
            getToken();
        }

        result = evalExp6();
        if(op.equals("-")) result = -result;

        return result;
    }
    // 괄호 처리하기
    private double evalExp6() throws ParserException
    {
        double result;

        if(token.equals("(")) {
            getToken();
            result = evalExp2();

            if(!token.equals(")"))
                handleErr(UNBALPARENS);
            getToken();
        }
        else result = atom();
        return result;
    }
    // 숫자나 변수 값 가져오기
    private String atom() throws ParserException
    {
        ArrayList ary = new ArrayList();
        int i =0;
        String[] str = new String[10];
        String result = 0.0;
        switch(tokType) {
            case NUMBER:
                IsitString = false;
                try {
                    result = Double.parseDouble(token);}
                catch (NumberFormatException exc) {
                    handleErr(SYNTAX); }
                getToken();
                break;
            case VARIABLE:
                result = findVar(token);
                getToken();
                break;
            case STRING:
                IsitString = true;
                str[i] = token;
                i++;
                result = findStr(token);
                getToken();
                break;

            default:
                handleErr(SYNTAX);
                break;
        }
        return result;
    }
    private double findStr(String str) throws ParserException {
        strvar = str;
        strIdx++;
        return strIdx;
    }
    // 인덱스 값 되돌리기
    private void putBack()
    {
        if(token == EOE) return;
        for(int i=0; i < token.length(); i++) expIdx--;
    }
    // 에러 처리하기
    private double findVar(String vname) throws ParserException {
        if(!Character.isLetter(vname.charAt(0))){
            handleErr(SYNTAX);
            return 0.0;
        }
        return vars[Character.toUpperCase(vname.charAt(0))-'A'];
    }

    private void handleErr(int error) throws ParserException
    {
        String[] err = {
                "Syntax Error",
                "Unbalanced Parentheses",
                "No Expression Present",
                "Division by Zero",
        };
        throw new ParserException(err[error]);
    }

    // 다음 토큰값 가져오기
    private void getToken()
    {
        tokType = NONE;
        token = ""; // getToken 들어올때마다 초기화
// 표현식 끝인지 여부 확인
        if(expIdx == exp.length()) {
            token = EOE;
            return;
        }
// 공백 스킵
        while(expIdx < exp.length() && Character.isWhitespace(exp.charAt(expIdx))) ++expIdx; // 표현식 마지막 여부 확인
        if(expIdx == exp.length()) {
            token = EOE;
            return;
        }
        if(isDelim(exp.charAt(expIdx))) { // 연산자
            token += exp.charAt(expIdx);
            expIdx++;
            tokType = DELIMITER;
        }
        else if(Character.isLetter(exp.charAt(expIdx))) { // 변수
            while(!isDelim(exp.charAt(expIdx))) {
                token += exp.charAt(expIdx);
                expIdx++;
                if(expIdx >= exp.length()) break;
            }
            tokType = VARIABLE;
        }
        else if(Character.isDigit(exp.charAt(expIdx))) { // 숫자
            while(!isDelim(exp.charAt(expIdx))) {
                token += exp.charAt(expIdx);
                expIdx++;
                if(expIdx >= exp.length()) break;
            }
            tokType = NUMBER;
        }
        else if(isString(exp.charAt(expIdx))) { // 스트링
            while(!isDelim(exp.charAt(expIdx))) {
                token += exp.charAt(expIdx);
                expIdx++;
                if (exp.charAt(expIdx) == '"') {
                    expIdx++;
                    break;
                }
            }
            tokType = STRING;
        }
        else { // 모르는 형태는 종료
            token = EOE;
            return;
        }
    }
    // // true는 연산자 문자이다.
    private boolean isDelim(char c) {
        if((" +-/*%^=()".indexOf(c) != -1))
            return true;
        return false;
    }
    private boolean isString(char c) {
        if (c == '"') {
            expIdx++;
            return true;
        }
        else
            return false;
    }

}