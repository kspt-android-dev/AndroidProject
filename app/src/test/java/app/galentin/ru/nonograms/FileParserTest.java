package app.galentin.ru.nonograms;

import android.content.res.AssetManager;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileParserTest {

    @Test
    public void init() {
        FileParser pars = new FileParser();
        pars.init("nonograms10x10e1.txt");
        assertEquals(13, pars.getColumns());
        assertEquals("#8B4513", pars.getColor1());
        assertEquals("#FF8C00", pars.getColor2());

        FileParser pars2 = new FileParser();
        pars2.init("nonograms15x15e1.txt");
        assertEquals(19, pars2.getColumns());
        assertEquals("#FFA500", pars2.getColor1());
        assertEquals("#1E90FF", pars2.getColor2());
    }
}