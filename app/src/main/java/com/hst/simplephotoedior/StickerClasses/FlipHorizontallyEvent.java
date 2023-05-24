package com.hst.simplephotoedior.StickerClasses;

public class FlipHorizontallyEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_HORIZONTALLY;
  }
}