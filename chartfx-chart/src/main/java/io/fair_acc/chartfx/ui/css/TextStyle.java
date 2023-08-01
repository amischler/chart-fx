package io.fair_acc.chartfx.ui.css;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

/**
 * An invisible node that lets users change styles
 * via CSS. The actual drawing is done in a canvas to
 * reduce the number of nodes on the SceneGraph. Each change
 * increments a counter, so that it is easy to invalidate outdated
 * renderings.
 *
 * @author ennerf
 */
public class TextStyle extends Text {

    public TextStyle(String... styles) {
        StyleUtil.hiddenStyleNode(this, styles);
        fontProperty().addListener(onChange);
        fillProperty().addListener(onChange);
        strokeProperty().addListener(onChange);
        textAlignmentProperty().addListener(onChange);
        textOriginProperty().addListener(onChange);
        strokeWidthProperty().addListener(onChange);
        opacityProperty().addListener(onChange);
        rotateProperty().addListener(onChange);
    }

    /**
     * Copies all style parameters except for rotate
     * @param gc target context
     */
    public void copyStyleTo(GraphicsContext gc) {
        gc.setFont(getFont());
        gc.setFill(getFill());
        gc.setStroke(getStroke());
        gc.setTextAlign(getTextAlignment());
        gc.setTextBaseline(getTextOrigin());
        gc.setLineWidth(getStrokeWidth());
        gc.setGlobalAlpha(getOpacity());
        // gc.rotate(getRotate()); // could cause issues with translation
    }

    public long getChangeCounter() {
        return changeCounter.get();
    }

    public ReadOnlyLongProperty changeCounterProperty() {
        return changeCounter;
    }

    LongProperty changeCounter = new SimpleLongProperty(0);
    ChangeListener<Object> onChange = (obs, old, value) -> changeCounter.set(changeCounter.get());

}
