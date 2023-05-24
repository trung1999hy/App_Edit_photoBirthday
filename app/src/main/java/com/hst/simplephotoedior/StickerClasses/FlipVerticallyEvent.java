package com.hst.simplephotoedior.StickerClasses;

public class FlipVerticallyEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY;
  }
}