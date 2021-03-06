package ru.unn.agile.geometry.viewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.geometry.Point;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    private LinePlainIntersection viewModel;

    @Before
    public void setUp() {
        viewModel = new LinePlainIntersection();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals(viewModel.getLinePx(), "");
        assertEquals(viewModel.getLinePy(), "");
        assertEquals(viewModel.getLinePz(), "");
        assertEquals(viewModel.getLineDirX(), "");
        assertEquals(viewModel.getLineDirY(), "");
        assertEquals(viewModel.getLineDirZ(), "");
        assertEquals(viewModel.getPlainPointX(), "");
        assertEquals(viewModel.getPlainPointY(), "");
        assertEquals(viewModel.getPlainPointZ(), "");
        assertEquals(viewModel.getPlainOrtX(), "");
        assertEquals(viewModel.getPlainOrtY(), "");
        assertEquals(viewModel.getPlainOrtZ(), "");
        assertEquals(viewModel.isCalcButtonEnabled(), false);
    }

    @Test
    public void whenInputIsIncorrectButtonDisabled() {
        viewModel.setLinePx("linePx");
        viewModel.setLinePy("linePy");
        viewModel.setLinePz("linePz");
        viewModel.setLineDirX("lineDirX");
        viewModel.setLineDirY("lineDirY");
        viewModel.setLineDirZ("lineDirZ");
        viewModel.setPlainPointX("plainPointX");
        viewModel.setPlainPointY("plainPointY");
        viewModel.setPlainPointZ("plainPointZ");
        viewModel.setPlainOrtX("plainOrtX");
        viewModel.setPlainOrtY("plainOrtY");
        viewModel.setPlainOrtZ("plainOrtZ");

        viewModel.inputSomething();

        assertEquals(viewModel.isCalcButtonEnabled(), false);
        assertEquals(viewModel.getResultX(), "Parse error");
    }

    @Test
    public void whenInputIsCorrectButtonEnabled() {
        viewModel.setLinePx("1");
        viewModel.setLinePy("1.0");
        viewModel.setLinePz("1.0");
        viewModel.setLineDirX("0");
        viewModel.setLineDirY("0.0");
        viewModel.setLineDirZ("0.0");
        viewModel.setPlainPointX("0.0");
        viewModel.setPlainPointY("0.0");
        viewModel.setPlainPointZ("0.0");
        viewModel.setPlainOrtX("0.0");
        viewModel.setPlainOrtY("0.0");
        viewModel.setPlainOrtZ("0.0");

        viewModel.inputSomething();

        assertEquals(viewModel.isCalcButtonEnabled(), true);
    }

    @Test
    public void canConvertToPoint() {
        String x = "0";
        String y = "-1.0";
        String z = "2e-1";

        Point result = viewModel.parsePoint(x, y, z);

        assertEquals(result, new Point(0, -1.0, 0.2));
    }

    @Test
    public void canConvertToPointSpecific() {
        String x = " 1.2e0";
        String y = "100 ";
        String z = " 6";

        Point result = viewModel.parsePoint(x, y, z);

        assertEquals(result, new Point(1.2, 100, 6));
    }

    @Test
    public void whenIntersectionExistSetResult() {
        viewModel.setLinePx("1");
        viewModel.setLinePy("2");
        viewModel.setLinePz("3");
        viewModel.setLineDirX("0");
        viewModel.setLineDirY("0");
        viewModel.setLineDirZ("1");
        viewModel.setPlainPointX("1");
        viewModel.setPlainPointY("2");
        viewModel.setPlainPointZ("3");
        viewModel.setPlainOrtX("0.0");
        viewModel.setPlainOrtY("0.0");
        viewModel.setPlainOrtZ("1.0");

        viewModel.inputSomething();
        viewModel.calc();

        Point result = viewModel.parsePoint(viewModel.getResultX(), viewModel.getResultY(), viewModel.getResultZ());

        assertEquals(result, new Point(1.0, 2.0, 3.0));
    }

    @Test
    public void whenClearResultsOnInput() {
        viewModel.setLinePx("1");
        viewModel.setLinePy("2");
        viewModel.setLinePz("3");
        viewModel.setLineDirX("0");
        viewModel.setLineDirY("0");
        viewModel.setLineDirZ("1");
        viewModel.setPlainPointX("1");
        viewModel.setPlainPointY("2");
        viewModel.setPlainPointZ("3");
        viewModel.setPlainOrtX("0.0");
        viewModel.setPlainOrtY("0.0");

        viewModel.inputSomething();

        viewModel.setPlainOrtZ("1.0");

        viewModel.inputSomething();
        assertEquals(viewModel.getResultX(), "");
    }

    @Test
    public void whenIntersectionNotExistSetMessage() {
        viewModel.setLinePx("0");
        viewModel.setLinePy("0");
        viewModel.setLinePz("1");
        viewModel.setLineDirX("1");
        viewModel.setLineDirY("1");
        viewModel.setLineDirZ("0");
        viewModel.setPlainPointX("0");
        viewModel.setPlainPointY("0");
        viewModel.setPlainPointZ("0");
        viewModel.setPlainOrtX("0.0");
        viewModel.setPlainOrtY("0.0");
        viewModel.setPlainOrtZ("1.0");

        viewModel.inputSomething();
        viewModel.calc();

        assertEquals(viewModel.getResultX(), "no intersection");
        assertEquals(viewModel.getResultY(), "no intersection");
        assertEquals(viewModel.getResultZ(), "no intersection");
    }
}
