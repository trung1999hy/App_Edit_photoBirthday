package com.hst.simplephotoedior.sticker;

public class FlipVerticallyEvent extends AbstractFlipEvent {

  @Override
  @StickerView.Flip protected int getFlipDirection() {
    return StickerView.FLIP_VERTICALLY;
  }
}