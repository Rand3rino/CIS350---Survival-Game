package Logic;

import entity.Player;
import entity.Walker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

    @Test
    public void inCombatTest(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(120,100,null,p,null);
        Combat c = new Combat();

        assertFalse(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest2(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(110,100,null,p,null);
        Combat c = new Combat();

        assertTrue(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest3(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(90,100,null,p,null);
        Combat c = new Combat();

        assertTrue(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest4(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(80,100,null,p,null);
        Combat c = new Combat();

        assertFalse(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest5(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(100,90,null,p,null);
        Combat c = new Combat();

        assertTrue(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest6(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(100,80,null,p,null);
        Combat c = new Combat();

        assertFalse(c.inCombat(w,p));
    }

    @Test
    public void inCombatTest7(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(100,110,null,p,null);
        Combat c = new Combat();

        assertTrue(c.inCombat(w,p));
    }

    @Test
    public void inCombat8(){
        Player p = new Player(100,100,null,null);
        Walker w = new Walker(100,120,null,p,null);
        Combat c = new Combat();

        assertFalse(c.inCombat(w,p));
    }

}