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
package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

/**
 * A model of a shape that contains points that can be modified.
 * @author Arnaud Blouin
 */
abstract class LModifiablePointsShape extends LShape implements IModifiablePointsShape {
	/**
	 * Creates the shape.
	 */
	LModifiablePointsShape() {
		super();
	}


	@Override
	public void rotate(final IPoint point, final double angle) {
		setRotationAngle(point, angle);
	}


	public void setRotationAngle(final IPoint gc, final double angle) {
		if(MathUtils.INST.isValidCoord(angle)) {
			final double diff = angle - getRotationAngle();
			final IPoint gc2 = gc == null ? getGravityCentre() : gc;

			super.setRotationAngle(angle);
			points.forEach(pt -> pt.setPoint(pt.rotatePoint(gc2, diff)));
		}
	}


	@Override
	public void setRotationAngle(final double angle) {
		setRotationAngle(null, angle);
	}


	@Override
	public boolean setPoint(final IPoint p, final int position) {
		return p != null && setPoint(p.getX(), p.getY(), position);
	}


	@Override
	public boolean setPoint(final double x, final double y, final int position) {
		if(!MathUtils.INST.isValidPt(x, y) || position < -1 || position > points.size() || points.isEmpty()) return false;

		final IPoint p = position == -1 ? points.get(points.size() - 1) : points.get(position);
		p.setPoint(x, y);

		return true;
	}


	@Override
	public boolean removePoint(final IPoint pt) {
		if(pt == null) return false;
		final int ind = points.indexOf(pt);
		return ind != -1 && removePoint(ind) != null;
	}


	@Override
	public IPoint removePoint(final int position) {
		if(!points.isEmpty() && position >= -1 && position < points.size()) {
			return points.remove(position == -1 ? points.size() - 1 : position);
		}
		return null;
	}


	@Override
	public IPoint replacePoint(final IPoint pt, final int position) {
		if(!MathUtils.INST.isValidPt(pt) || points.contains(pt) || position < -1 || position > points.size()) return null;

		final IPoint pRemoved = points.remove(position == -1 ? points.size() - 1 : position);

		if(position == -1 || points.isEmpty()) points.add(pt);
		else points.add(position, pt);

		return pRemoved;
	}


	@Override
	public void addPoint(final IPoint pt) {
		addPoint(pt, -1);
	}


	@Override
	public void addPoint(final IPoint pt, final int position) {
		if(MathUtils.INST.isValidPt(pt) && position >= -1 && position <= points.size())
			if(position == -1 || position == points.size()) points.add(pt);
			else points.add(position, pt);
	}
}
