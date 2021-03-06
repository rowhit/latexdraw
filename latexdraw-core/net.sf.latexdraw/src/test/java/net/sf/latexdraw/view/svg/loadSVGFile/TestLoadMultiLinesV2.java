package net.sf.latexdraw.view.svg.loadSVGFile;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.IPolyline;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestLoadMultiLinesV2 extends TestLoadSVGFile {

	@Override
	public String getPathSVGFile() {
		return "src/test/resources/testLoad/testMultiLines.svg"; //$NON-NLS-1$
	}

	@Override
	public int getNbShapesExpected() {
		return 1;
	}

	public IPolyline getLine() {
		return (IPolyline)group.getShapeAt(0);
	}

	@Test
	public void testFilling() {
		final IPolyline line = getLine();
		assertTrue(line.isFilled());
		assertEquals(ShapeFactory.INST.createColorInt(183, 44, 44), line.getFillingCol());
		assertEquals(FillingStyle.PLAIN, line.getFillingStyle());
	}

	@Test
	public void testPoints() {
		final IPolyline line = getLine();
		assertEquals(6, line.getNbPoints());
		assertEquals(260.0, line.getPtAt(0).getX(), 0.001);
		assertEquals(440.0, line.getPtAt(0).getY(), 0.001);
		assertEquals(260.0, line.getPtAt(1).getX(), 0.001);
		assertEquals(220.0, line.getPtAt(1).getY(), 0.001);
		assertEquals(520.0, line.getPtAt(2).getX(), 0.001);
		assertEquals(220.0, line.getPtAt(2).getY(), 0.001);
		assertEquals(640.0, line.getPtAt(3).getX(), 0.001);
		assertEquals(300.0, line.getPtAt(3).getY(), 0.001);
		assertEquals(760.0, line.getPtAt(4).getX(), 0.001);
		assertEquals(220.0, line.getPtAt(4).getY(), 0.001);
		assertEquals(760.0, line.getPtAt(5).getX(), 0.001);
		assertEquals(120.0, line.getPtAt(5).getY(), 0.001);
	}

	@Test
	public void testLine() {
		final IPolyline line = getLine();
		assertEquals(10.0, line.getThickness(), 0.01);
		assertEquals(ShapeFactory.INST.createColorInt(22, 131, 175), line.getLineColour());
		assertEquals(LineStyle.DASHED, line.getLineStyle());
	}

	@Test
	public void testShadow() {
		final IPolyline line = getLine();
		assertTrue(line.hasShadow());
		assertEquals(ShapeFactory.INST.createColorInt(101, 224, 41), line.getShadowCol());
		assertEquals(80.0, Math.toDegrees(line.getShadowAngle()), 0.00001);
		assertEquals(20.0, line.getShadowSize(), 0.0001);
	}

	@Test
	public void testArrows() {
		final IPolyline line = getLine();
		assertEquals(ArrowStyle.LEFT_SQUARE_BRACKET, line.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_END, line.getArrowStyle(-1));
		assertEquals(2.2, line.getDotSizeNum(), 0.0001);
		assertEquals(5.0, line.getDotSizeDim(), 0.0001);
		assertEquals(0.25, line.getBracketNum(), 0.0001);
		assertEquals(4.0, line.getTBarSizeDim(), 0.0001);
		assertEquals(10.0, line.getTBarSizeNum(), 0.0001);
	}

	@Test
	public void testDbleBorder() {
		final IPolyline line = getLine();
		assertTrue(line.hasDbleBord());
		assertEquals(ShapeFactory.INST.createColorInt(213, 240, 66), line.getDbleBordCol());
		assertEquals(4, line.getDbleBordSep(), 0.001);
	}
}
