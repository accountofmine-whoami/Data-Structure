import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
 
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
 
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");
    public int[] magnitude;
    public char sign;
    
    public BigInteger()
    {
    }
 
    public BigInteger(int[] num1, char sign)
    {
            this.magnitude = num1;
            this.sign = sign;
    }
 
    public BigInteger(String s)
    {
        int length = s.length();
        int i;
        if(s.charAt(0) == '-' || s.charAt(0) == '+'){
        	this.magnitude = new int[length-1];
        	this.sign = s.charAt(0);
        	for(i=0;i<length-1;i++){
        		this.magnitude[i] =  (int)s.charAt(i+1) - 48;
        	}
        }else{
        	this.magnitude = new int[length];
        	this.sign = '+';
        	for(i=0;i<length;i++){
        		this.magnitude[i] = (int)s.charAt(i) - 48;
        	}
        }
    }
 
    public BigInteger add(BigInteger big)
    {
    	 if(this.sign == big.sign){
   		 BigInteger big2 = new BigInteger();
   		 big2.sign = big.sign;
   		 
   		 int big_l = big.magnitude.length, this_l = this.magnitude.length;
   		 
   		 if(big_l >= this_l){
   			 
   			 big2.magnitude = new int[big_l+1];
   			 int i, carry=0;
   			 for(i=0;i<this_l;i++){
   				big2.magnitude[big_l -i] = (big.magnitude[big_l-1-i] + this.magnitude[this_l -1 -i] +carry )%10;
   				carry =  (big.magnitude[big_l-1-i] + this.magnitude[this_l -1 -i] +carry )/10;;
   			 }
   			 
   			 for(;i<big_l;i++){
   				big2.magnitude[big_l - i] = (big.magnitude[big_l -1 - i] + carry) % 10;
   				carry = (big.magnitude[big_l -1 - i] + carry) / 10;
   			 }
   			 
   			big2.magnitude[0] = carry;
   			
   		 }else{
   			 big2.magnitude = new int[this_l+1];
   			 int i, carry=0;
   			 for(i=0;i<big_l;i++){
   				big2.magnitude[this_l -i] = (this.magnitude[this_l-1-i] + big.magnitude[big_l -1 -i] +carry )%10;
   				carry = (this.magnitude[this_l-1-i] + big.magnitude[big_l -1 -i] +carry)/10; 

   			 }
   			 
   			 for(;i<this_l;i++){
   				 big2.magnitude[this_l - i] = (this.magnitude[this_l - 1 -i] + carry)%10;
   				 carry = (this.magnitude[this_l - 1 -i] + carry)/10;

   			 }
   			 big2.magnitude[0] = carry;
   		 }
 
   		 return big2;
   	 	} // sum with same signs
        else{
            if(this.sign == '-'){
                this.sign = '+';
                return big.subtract(this); 
            }else{
                big.sign = '+';
                return this.subtract(big);
            }
        } // sum with different signs => subtraction
    }
 
    public BigInteger subtract(BigInteger big)
    {
    	if(this.sign == big.sign){
    		if(this.sign == '-'){
    			this.sign = '+';
    			big.sign = '+';
    			return big.subtract(this);
    		} // case (negative - negative)
    		else{
    			BigInteger big2 = new BigInteger();
    			int tl = this.magnitude.length, bl = big.magnitude.length; 
    			
    			if(tl == bl){
    				int i = 0;
    				
    				while(i<tl && this.magnitude[i] == big.magnitude[i]){ i++; }
    				
    				if(i == tl || this.magnitude[i] > big.magnitude[i]){
    					big2.sign = '+';
    					big2.magnitude = new int[tl];
    				}else{
    					big2.sign = '-';
    					big2.magnitude = new int[bl];
    				}
    				
    			}else if(bl > tl){
    				big2.sign = '-';
    				big2.magnitude = new int[bl];
    			}else{
    				big2.sign = '+';
    				big2.magnitude = new int[tl];
    			} //determinig the signs, length of big2.
    			
    			
    			int i, borrow = 0;
    			if(big2.sign == '+'){
    				
    				for(i=0;i < bl; i++){
    					big2.magnitude[tl -1-i]= (this.magnitude[tl - 1 -i] - borrow - big.magnitude[bl - 1 -i])<0 ? (this.magnitude[tl - 1 -i] - borrow - big.magnitude[bl - 1 -i])+10 : (this.magnitude[tl - 1 -i] - borrow - big.magnitude[bl - 1 -i]) ;
    					borrow = (this.magnitude[tl - 1 -i] - borrow - big.magnitude[bl - 1 -i])<0? 1:0;
    				}
    				for(;i<tl;i++){
    					big2.magnitude[tl -1-i]= (this.magnitude[tl - 1 -i] - borrow )<0? (this.magnitude[tl - 1 -i] - borrow )+10: (this.magnitude[tl - 1 -i] - borrow ) ;
    					borrow = (this.magnitude[tl - 1 -i] - borrow )<0? 1:0;
    				}
    				
    			}else{
    				
    				for(i=0;i < tl; i++){
    					big2.magnitude[bl -1-i]= (big.magnitude[bl - 1 -i] - borrow - this.magnitude[tl - 1 -i])<0? (big.magnitude[bl - 1 -i] - borrow - this.magnitude[tl - 1 -i])+10: (big.magnitude[bl - 1 -i] - borrow - this.magnitude[tl - 1 -i]) ;
    					borrow = (big.magnitude[bl - 1 -i] - borrow - this.magnitude[tl - 1 -i])<0? 1:0;
    				}
    				for(;i<bl;i++){
    					big2.magnitude[bl -1-i]= (big.magnitude[bl - 1 -i] - borrow )<0 ? (big.magnitude[bl - 1 -i] - borrow )+10:(big.magnitude[bl - 1 -i] - borrow ) ;
    					borrow = (big.magnitude[bl - 1 -i] - borrow )<0? 1:0 ;
    				}
    				
    			}//calculating magnitude
    			
    			return big2;
    		} // case (positive - positive)
    	}else{
    		if(this.sign == '-'){
    			big.sign = '-';
    			return this.add(big);
    			//case (negative - positive) => use addition!!
    		}else{
    			big.sign = '+';
    			return this.add(big);
    			//case (positive - negative) => use addition!!
    		}
    	}
    }
 
    public BigInteger multiply(BigInteger big)
    {
    	BigInteger big2 = new BigInteger();
    	big2.magnitude = new int[this.magnitude.length + big.magnitude.length];
    	
    	char this_sign = this.sign, big_sign = big.sign;
    	this.sign = '+';
    	big.sign = '+';
    	big2.sign = '+';
    	
    	BigInteger temp =new BigInteger();
    	int i;
    	for(i=0;i<big.magnitude.length;i++){
    		temp = this.multiply_ones(big,i);
    		big2 = big2.add(temp);
    	}

    	if(big_sign != this_sign) big2.sign = '-';
    	return big2;
    }
    
    public BigInteger multiply_ones(BigInteger big, int zeros){
    	BigInteger big2 = new BigInteger();
    	
    	big2.magnitude = new int[this.magnitude.length + 1 + zeros];
    	big2.sign = '+';
    	int length = big2.magnitude.length;
    	
    	int i;
    	int mult = big.magnitude[big.magnitude.length -1 -zeros];
    	for(i=0;i<zeros;i++){
    		big2.magnitude[length - 1 - i] = 0;
    	}
    	int carry = 0;
    	for(i=0;i<this.magnitude.length;i++){
    		big2.magnitude[this.magnitude.length -i] = (this.magnitude[this.magnitude.length -1 -i]*mult + carry)%10;
    		carry = (this.magnitude[this.magnitude.length -1 -i]*mult + carry)/10;
    	}
    	big2.magnitude[0] = carry;
    	return big2;
    }
    @Override

    public String toString(){
    	String s = "";
    	int i=0, length = magnitude.length;
    	while(i<length && this.magnitude[i] ==0){ i++;}
    	
    	if(i==length) {return "0";}
    	
    	if(this.sign == '-'){s = "-";}
    	while(i<=length-1){
    		s = s + magnitude[i] ;
    		i++;
    	}
    	return s;
    }
    
    /*public void printout(){
    	int i=0;
    	int length = this.magnitude.length;
    	
    	while(i<length && this.magnitude[i] ==0){ i++;}
    	if(i==length) {System.out.println('0'); return;}
    	if(this.sign == '-'){System.out.print(this.sign);}
    	while(i<length-1){
    		System.out.print(this.magnitude[i]);
    		i++;
    	}
    	System.out.println(this.magnitude[length-1]);
    }*/
 
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        int i=1;
        char op;
        String arg1, arg2;
        
        input = input.replaceAll("\\s+","");
        while('0'<=input.charAt(i) && input.charAt(i)<='9'){
            i++;
        }
        arg1 = input.substring(0,i);
        while(input.charAt(i) != '+' && input.charAt(i) != '*' && input.charAt(i) != '-'){
            i++;
        }
        op = input.charAt(i);
        arg2 = input.substring(i+1);
        
        
        BigInteger num1 = new BigInteger(arg1);
        BigInteger num2 = new BigInteger(arg2);
        BigInteger result = new BigInteger();
        
        switch(op){
        case '+' : result = num1.add(num2); break;
        case '-' : result = num1.subtract(num2); break;
        case '*' : result = num1.multiply(num2); break;
        }
        
        return result;
    }
 
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
 
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
 
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
 
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
            return false;
        }
    }
 
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}