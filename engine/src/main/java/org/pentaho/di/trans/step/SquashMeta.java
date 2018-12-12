package org.pentaho.di.trans.step;

import org.pentaho.di.base.BaseMeta;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.gui.Point;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

public class SquashMeta implements BaseMeta {
  private final Point location;
  private final String name;

  public SquashMeta( Point location, String name ) {
    this.location = location;
    this.name = name;
  }

  public SquashMeta( Node squashNode ) {
    name = XMLHandler.getTagValue( squashNode, "name" );
    location = new Point(
      Integer.parseInt( XMLHandler.getTagValue( squashNode, "x" ) ),
      Integer.parseInt( XMLHandler.getTagValue( squashNode, "y" ) ) );
  }

  @Override public Point getLocation() {
    return location;
  }

  @Override public String getName() {
    return name;
  }

  public String getXML() throws KettleException {
    @SuppressWarnings( "StringBufferReplaceableByString" )
    StringBuilder retval = new StringBuilder( 200 );
    retval.append( "  " ).append( XMLHandler.openTag( "squash" ) ).append( Const.CR );
    retval.append( "    " ).append( XMLHandler.addTagValue( "name", getName() ) );
    retval.append( "    " ).append( XMLHandler.addTagValue( "x", location.x ) );
    retval.append( "    " ).append( XMLHandler.addTagValue( "y", location.y ) );
    retval.append( "  " ).append( XMLHandler.closeTag( "squash" ) ).append( Const.CR );
    return retval.toString();
  }
}
