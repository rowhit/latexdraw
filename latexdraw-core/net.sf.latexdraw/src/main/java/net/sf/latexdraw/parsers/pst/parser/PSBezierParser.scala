/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */

package net.sf.latexdraw.parsers.pst.parser

import net.sf.latexdraw.models.ShapeFactory
import net.sf.latexdraw.models.interfaces.shape.IShape

/**
 * A parser grouping parsers parsing bézier curves.
 * @author Arnaud BLOUIN
 */
trait PSBezierParser extends PSTAbstractParser with PSTParamParser with PSTCoordinateParser with PSTBracketBlockParser {
	/**
	 * Parses psbezier commands.
	 */
	def parsePsbezier(ctx : PSTContext) : Parser[List[IShape]] =
		("\\psbezier*" | "\\psbezier") ~ opt(parseParam(ctx)) ~ opt(parseBracket(ctx)) ~ rep(repN(3, parseCoord(ctx))) ~ opt(parseCoord(ctx)) ^^ {
			case cmdName ~ _ ~ arrowRaw ~ ptsRaw ~ ptRaw =>

		val listPts = ptRaw match {
			case Some(value) => ptsRaw.flatten ::: List(value)
			case None => ctx.origin.dup :: ptsRaw.flatten
		}

		val bezier = ShapeFactory.INST.createBezierCurve()
		var j = 1
		val size = listPts.length

		// Setting the points.
		for(i <- 0 until size by 3) bezier.addPoint(transformPointTo2DScene(listPts(i), ctx))

		for(i <- 2 until size by 3){
			bezier.getFirstCtrlPtAt(j).setPoint(transformPointTo2DScene(listPts(i), ctx))
			j +=1
		}

		if(size>1)
			bezier.getFirstCtrlPtAt(0).setPoint(transformPointTo2DScene(listPts(1), ctx))

		setShapeParameters(bezier, ctx)
		setArrows(bezier, arrowRaw, false, ctx)
		bezier.updateSecondControlPoints()
		
		if(bezier.getNbPoints>2 && bezier.getPtAt(0).equals(bezier.getPtAt(-1))) {
		  bezier.removePoint(-1)
		}
		else
		  bezier.setIsClosed(false)

		if(cmdName.endsWith("*"))
			setShapeForStar(bezier)

		checkTextParsed(ctx) ::: List(bezier)
	}
}
