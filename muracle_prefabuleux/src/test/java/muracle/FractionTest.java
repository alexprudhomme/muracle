package muracle;

import muracle.utilitaire.Fraction;
import muracle.utilitaire.FractionError;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

public class FractionTest {

    @Test
    public void fraction(){
        try{
            new Fraction(5,26);
            assertTrue(true);
        }catch (FractionError fractionError) {
            fail("Pas D'erreur à été catch");
        }
        try{
            new Fraction(5,0);
            fail("Pas D'erreur à été catch");
        }catch (FractionError fractionError) {
            assertTrue(true);
        }
    }

    @Test
    public void setNum(){
        try{
            Fraction frac1 = new Fraction(5,26);
            assertThat(frac1.getNum()).isEqualTo(5);
            frac1.setNum(4);
            assertThat(frac1.getNum()).isEqualTo(4);
        }catch (FractionError fractionError) {
            fail();
        }
    }

    @Test
    public void setDenum(){
        try{
            Fraction frac1 = new Fraction(5,26);
            assertThat(frac1.getDenum()).isEqualTo(26);
            frac1.setDenum(20);
            assertThat(frac1.getDenum()).isEqualTo(20);
        }catch (FractionError fractionError) {
            fail();
        }
    }

    @Test
    public void checkNeg(){
        try{
            Fraction frac1 = new Fraction(-1,-5);
            Fraction frac2 = new Fraction(1,-5);
            Fraction frac3 = new Fraction(-1,5);
            assertThat(frac1.getNum()).isEqualTo(1);
            assertThat(frac1.getDenum()).isEqualTo(5);
            assertThat(frac2.getNum()).isEqualTo(-1);
            assertThat(frac2.getDenum()).isEqualTo(5);
            assertThat(frac3.getNum()).isEqualTo(-1);
            assertThat(frac3.getDenum()).isEqualTo(5);
        }catch (FractionError fractionError) {
            fail();
        }
    }

    @Test
    public void toDouble(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(2,10);
            Fraction frac3 = new Fraction(4,25);
            assertThat(frac1.toDouble()).isEqualTo(0.2);
            assertThat(frac2.toDouble()).isEqualTo(0.2);
            assertThat(frac3.toDouble()).isEqualTo(0.16);
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void equal(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(2,10);
            Fraction frac3 = new Fraction(1,5);
            Fraction frac4 = new Fraction(4,25);
            assertTrue(frac1.equals(frac3));
            assertTrue(frac1.equals(frac2));
            assertFalse(frac1.equals(frac4));

        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void compare(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(2,10);
            Fraction frac3 = new Fraction(4,25);
            assertThat(frac1.compare(frac2)).isEqualTo(0);
            assertThat(frac1.compare(frac3)).isEqualTo(1);
            assertThat(frac3.compare(frac2)).isEqualTo(-1);

        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void addRefFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            assertTrue(frac1.addRef(frac2).equals(new Fraction(3,10)));
            assertTrue(frac1.addRef(frac3).equals(new Fraction(1,2)));
            assertTrue(frac1.addRef(frac4).equals(new Fraction(0,2)));
            assertTrue(frac1.addRef(frac4).equals(new Fraction(-1,2)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void addRefInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.addRef(3).equals(new Fraction(16,5)));
            assertTrue(frac1.equals(new Fraction(16,5)));
            assertTrue(frac1.addRef(-4).equals(new Fraction(-4,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void addFaction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            Fraction frac5 = frac1.add(frac2);
            assertTrue(frac5.equals(new Fraction(3,10)));
            frac5 = frac5.add(frac3);
            assertTrue(frac5.equals(new Fraction(1,2)));
            frac5 = frac5.add(frac4);
            assertTrue(frac5.equals(new Fraction(0,2)));
            frac5 = frac5.add(frac4);
            assertTrue(frac5.equals(new Fraction(-1,2)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void addInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.add(3).equals(new Fraction(16,5)));
            assertTrue(frac1.equals(new Fraction(1,5)));
            assertTrue(frac1.add(-4).equals(new Fraction(-19,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void subRefFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            assertTrue(frac1.subRef(frac2).equals(new Fraction(1,10)));
            assertTrue(frac1.subRef(frac3).equals(new Fraction(-1,10)));
            assertTrue(frac1.subRef(frac4).equals(new Fraction(2,5)));
            assertTrue(frac1.subRef(frac4).equals(new Fraction(9,10)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void subRefInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.subRef(3).equals(new Fraction(-14,5)));
            assertTrue(frac1.equals(new Fraction(-14,5)));
            assertTrue(frac1.subRef(-4).equals(new Fraction(6,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void subFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            Fraction frac5 = frac1.sub(frac2);
            assertTrue(frac5.equals(new Fraction(1,10)));
            frac5 = frac5.sub(frac3);
            assertTrue(frac5.equals(new Fraction(-1,10)));
            frac5 = frac5.sub(frac4);
            assertTrue(frac5.equals(new Fraction(2,5)));
            frac5 = frac3.sub(frac4);
            assertTrue(frac5.equals(new Fraction(7,10)));
            assertTrue(frac3.equals(new Fraction(2,10)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void subInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.sub(3).equals(new Fraction(-14,5)));
            assertTrue(frac1.equals(new Fraction(1,5)));
            assertTrue(frac1.sub(-4).equals(new Fraction(21,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void mulRefFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            assertTrue(frac1.mulRef(frac2).equals(new Fraction(1,50)));
            assertTrue(frac1.mulRef(frac3).equals(new Fraction(1,250)));
            assertTrue(frac1.mulRef(frac4).equals(new Fraction(-1,500)));
            assertTrue(frac1.mulRef(frac4).equals(new Fraction(1,1000)));

            Fraction frac5 = new Fraction(1,5);
            assertThat(frac5.mulRef(3).equals(new Fraction(3,5))).isTrue();
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void mulRefInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.mulRef(2).equals(new Fraction(2,5)));
            assertTrue(frac1.equals(new Fraction(2,5)));
            assertTrue(frac1.mulRef(5).equals(new Fraction(2,1)));
            assertTrue(frac1.equals(new Fraction(2,1)));
            assertTrue(frac1.mulRef(-5).equals(new Fraction(-10,1)));
            assertTrue(frac1.equals(new Fraction(-10,1)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void mulFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            Fraction frac5 = frac1.mul(frac2);
            assertTrue(frac5.equals(new Fraction(1,50)));
            frac5 = frac5.mul(frac3);
            assertTrue(frac5.equals(new Fraction(1,250)));
            frac5 = frac5.mul(frac4);
            assertTrue(frac5.equals(new Fraction(-1,500)));
            frac5 = frac3.mul(frac4);
            assertTrue(frac5.equals(new Fraction(-1,10)));
            assertTrue(frac3.equals(new Fraction(2,10)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void mulInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            assertTrue(frac1.mul(2).equals(new Fraction(2,5)));
            assertTrue(frac1.equals(new Fraction(1,5)));
            assertTrue(frac1.mul(10).equals(new Fraction(2,1)));
            assertTrue(frac1.equals(new Fraction(1,5)));
            assertTrue(frac1.mul(-10).equals(new Fraction(-2,1)));
            assertTrue(frac1.equals(new Fraction(1,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void divRefFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);

            assertTrue(frac1.divRef(2).equals(new Fraction(1,10)));
            assertTrue(frac1.equals(new Fraction(1,10)));
            assertTrue(frac1.divRef(-5).equals(new Fraction(-1,50)));
            assertTrue(frac1.equals(new Fraction(-1,50)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void divRefInt(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            assertTrue(frac1.divRef(frac2).equals(new Fraction(2,1)));
            assertTrue(frac1.divRef(frac3).equals(new Fraction(10,1)));
            assertTrue(frac1.divRef(frac4).equals(new Fraction(-20,1)));
            assertTrue(frac1.divRef(frac4).equals(new Fraction(40,1)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void divFraction(){
        try{
            Fraction frac1 = new Fraction(1,5);

            assertTrue(frac1.div(2).equals(new Fraction(1,10)));
            assertTrue(frac1.equals(new Fraction(1,5)));
            assertTrue(frac1.div(-5).equals(new Fraction(-1,25)));
            assertTrue(frac1.equals(new Fraction(1,5)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void div(){
        try{
            Fraction frac1 = new Fraction(1,5);
            Fraction frac2 = new Fraction(1,10);
            Fraction frac3 = new Fraction(2,10);
            Fraction frac4 = new Fraction(-1,2);
            Fraction frac5 = frac1.div(frac2);
            assertTrue(frac5.equals(new Fraction(2,1)));
            frac5 = frac5.div(frac3);
            assertTrue(frac5.equals(new Fraction(10,1)));
            frac5 = frac5.div(frac4);
            assertTrue(frac5.equals(new Fraction(-20,1)));
            frac5 = frac3.div(frac4);
            assertTrue(frac5.equals(new Fraction(-2,5)));
            assertTrue(frac3.equals(new Fraction(2,10)));
        }catch (FractionError fractionError){
            fail();
        }
    }

    @Test
    public void round(){
        try {
            Fraction frac1 = new Fraction(5, 256);
            Fraction frac2 = new Fraction(1, 10);
            Fraction frac3 = new Fraction(456, 87);
            assertTrue(frac1.round(128).equals(new Fraction(3,128)));
            assertTrue(frac2.round(100).equals(new Fraction(1,10)));
            assertTrue(frac2.round(128).equals(new Fraction(13,128)));
            assertTrue(frac3.round(128).equals(new Fraction(671,128)));
            try{
                assertTrue(frac3.round(0).equals(new Fraction(671,128)));
                fail();
            }catch (FractionError fractionError){
                assertTrue(true);
            }
            try{
                assertTrue(frac3.round(-5).equals(new Fraction(671,128)));
                fail();
            }catch (FractionError fractionError){
                assertTrue(true);
            }
        }
        catch (FractionError fractionError){
            fail();
        }
    }
}
