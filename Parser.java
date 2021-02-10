package lalalu;

	class ParserException extends Exception {
		String errStr;
		public ParserException(String str) {
			errStr = str;
		}
		
		public String toString() {
			return errStr;
		}
	}
	
	class Parser {
		final int NONE = 0;
		final int DELIMITER = 1;
		final int VARIABLE = 2;
		final int NUMBER = 3;
		//����Ÿ�� ���
		final int SYNTAX = 0;
		final int UNBALPARENS = 1;
		final int NOEXP = 2;
		final int DIVBYZERO = 3;
		
		final String EOE ="\0";
		
		private String exp;
		private int expIdx;
		public String token;
		private int tokType;
		
		private double vars[] = new double[26]; //���ĺ� 26����
		
		public double evaluate(String expstr) throws ParserException
		{
			double result;
			exp = expstr;
			expIdx = 0;
			
			getToken();
			if(token.equals(EOE))
				handleErr(NOEXP);
			
			result = evalExp2();
			
			if(!token.equals(EOE))
			handleErr(SYNTAX);
			
			return result;
		}
		
		private double evalExp2() throws ParserException
		{
			char op;
			double result;
			double partialResult;
			result = evalExp3();
			
			while((op=token.charAt(0)) == '+' || op == '-') {
				getToken();
				partialResult = evalExp3();
				switch(op) {
				case '-':
					result = result - partialResult;
					break;
				case '+':
					result = result + partialResult;
					break;
				}
			}
			return result;
		}
		
		private double evalExp3() throws ParserException
		{
			char op;
			double result;
			double partialResult;
			
			result = evalExp4();
			
			while((op = token.charAt(0)) == '*' || op == '/' || op == '%') {
				getToken();
				partialResult = evalExp4();
				switch(op) {
				case '*':
					result = result * partialResult;
					break;
				case'/':
					if(partialResult == 0.0)
						handleErr(DIVBYZERO);
					result = result / partialResult;
					break;
				case'%':
					if(partialResult == 0.0)
						handleErr(DIVBYZERO);
					result = result % result;
					break;
				}
			}
			return result;
		}
		
		//������ ó���Ѵ�
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
				if (partialResult == 0.0) {
					result = 1.0;
				} else
					for(t=(int)partialResult-1;t>0;t--)
						result = result * ex;
			}
			return result;
		}
		//������ ó���Ѵ�
		private double evalExp5() throws ParserException
		{
			double result;
			String op;
			
			op="";
			if((tokType == DELIMITER) && token.equals("+") || token.equals("-")) {
				op = token;
				getToken();
			}
			result = evalExp6();
			if(op.equals("-")) result = -result;
			
			return result;
		}
		
		//��ȣ ó��
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
		
		//���� �Ǵ� ���� ��
		private double atom() throws ParserException
		{
			double result = 0.0;
			
			switch(tokType) {
			case NUMBER:
				try {
					result = Double.parseDouble(token);
				} catch (NumberFormatException exc) {
					handleErr(SYNTAX);
				}
				getToken();
				break;
			case VARIABLE:
				result = findVar(token);
				getToken();
				break;
			default:
				handleErr(SYNTAX);
				break;
			}
			return result;
		}
		
		// ���� �� ����
		private double findVar(String vname) throws ParserException
		{
			if(!Character.isLetter(vname.charAt(0))) {
				handleErr(SYNTAX);
				return 0.0;
			}
			return vars[Character.toUpperCase(vname.charAt(0))-'A'];
		}
		//�Է� ��Ʈ�� ��  ��ŭ �ε��� �ǵ�����
		private void putBack()
		{
			if(token == EOE ) return;
			for(int i=0; i<token.length();i++) expIdx--;
		}
		//����ó��
		private void handleErr(int error) throws ParserException
		{
			String[] err = {
					"Syntax Error",
					"Unbalanced Parentheses",
					"No Expression Present",
					"Division by Zero"
			};
			
			throw new ParserException(err[error]);
		}
		
		//���� ��ū��
		private void getToken()
		{
			tokType = NONE;
			token ="";
			
		//ǥ���� �� ���� Ȯ��
		if(expIdx == exp.length()) {
			token = EOE;
			return;
		}
		if(isDelim(exp.charAt(expIdx))) {
			token += exp.charAt(expIdx);
			expIdx++;
			tokType = DELIMITER;
		}
		else if(Character.isLetter(exp.charAt(expIdx))) {
			while(!isDelim(exp.charAt(expIdx))) {
				token +=exp.charAt(expIdx);
				expIdx++;
				if(expIdx>=exp.length()) break;
			}
			tokType = NUMBER;
		}
		else { // ���ǵ��� ���� ���� ���ڿ� ����� ����
			token = EOE;
			return;
		}
		}
		private boolean isDelim(char c)
		{
			if(("+-/*%^=()".indexOf(c)!= -1))
				return true;
			return false;
		}
	}