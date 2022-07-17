import org.perlito.Perlito5.*;
import javax.script.*;
import org.perlito.Perlito5.*; 

/**
 * A quick and dirty test of basic perlito5 functionality.
 */
public final class Main
{
    public static void main(String[] args) throws ScriptException
    {
        ScriptEngineManager man = new ScriptEngineManager();
        // | javax.script.ScriptEngineManager@2d98a33
        System.out.println("00: " + man);
        
        // | org.perlito.Perlito5.Perlito5ScriptEngine@33bc72d1
        ScriptEngine se = man.getEngineByName("Perl5");
        System.out.println("01: " + se);

        // | a ref test "REF(0x3543df7d)"
        System.out.println("02: " + se.eval("\\2"));
        System.out.println("02:00: " + se.eval("\\2").getClass().getName());        
        
        System.out.println("03: " + se.eval("'Hello, world!'"));
        
        // | my gives inconsistent results on this and the statement below
        System.out.println("04: " + se.eval("our $foo = sub { return 3; }"));

        // | CODE(0x486be205)
        Object objObj00 = se.eval("$foo");
        System.out.println("05: " + objObj00);

        // | CODE(0x486be205)
        System.out.println("06: " + se.eval("return sub { return 3; }"));
        
        // | more CODE(0x...)
        System.out.println("07: " + se.eval("(sub { return sub { return 3; } })->()"));


        // | NOTE: PlLvalue is NOT P"I"Lvalue, P"lowerL"Lvalue.
        // | org.perlito.Perlito5.PlLvalue
        System.out.println("07:00: " + se.eval("(sub { return sub { return 3; } })->()").getClass().getName());
        
        // | something more fun, a hash mapping scalars to CODE refs.
        se.eval (
"our %obj1 = ( \n" +
"    greet1 => sub { print \"Hey!\"; },\n" +
"    greet2 => sub { print \"Yo!\"; },\n" +
"    greet3 => sub { print \"Sup!\"; },\n" +
"    greet4 => sub { print \"Hello!\"; },\n" +
"    greet5 => sub { print \"Well Met!\"; },\n" +
")");
        // | HASH(R1), we will see R1 again later.
        System.out.println("07:01: " +  se.eval("\\%obj1"));

        // | 5        
        System.out.println("08: " + se.eval("scalar keys %obj1"));
        
        // | org.perlito.Perlito5.PlLvalue
        System.out.println("09: " + se.eval("\\%obj1").getClass().getName());

        // | org.perlito.Perlito5.PlLvalue
        System.out.println("10: " + se.eval("%obj1").getClass().getName());
        
        // | "R1", successfully cast it out of PlLvalue       
        PlLvalue foo = (PlLvalue)se.eval("\\%obj1");
        PlHash hash1 = foo.hash_deref_strict();
        System.out.println("11: " + Integer.toHexString(Integer.parseInt(hash1.toString())));
        
        // | true
        System.out.println("12: " + hash1.is_hash());
        
        // | CODE(0x64f9f455), PlEval159$1$5
        PlObject objObj01 = hash1.hget("greet5");
        System.out.println("13: " + objObj01 + ", " + objObj01.getClass().getName());
        
        // | "hi", test a bunch of more advanced features.
        se.eval("use v5; use feature 'say'; use strict; say \"14: hi\";");
        
        // 3.141592653589793
        se.eval("package Math { import => \"java.lang.Math\" }; $x = Math->PI; print(\"15: $x\n\"); ");

        // 3.141592653589793, turns out no need of above statement.
        se.eval("$x = Math->PI; print(\"16: $x\n\"); ");
                
        se.put("Gift", new Gift());
        
        // | true
        PlObject ob = (PlObject)se.eval("our $fnc = sub { print \"called, $_[4] [\".scalar @_.\"]\\n\"; $_[5]->greet(); };");
        System.out.println("17: " + ob.is_coderef());
        System.out.println("18: " + ob.getClass().getName());
        PlLvalue ob2 = (PlLvalue)ob;
        PlArray args1 = new PlArray();
        args1.aset(0, "All");
        args1.aset(1, "glory");
        args1.aset(2, "to");
        args1.aset(3, "the");
        args1.aset(4, "hypnotoad");
        args1.aset(5, new Gift()); 
        // ob will invoke our Gift utility.        
        ob.apply(1, args1);
    }
}