package com.factionsstorm.Tool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.factionsstorm.Sc;

public class Drawer {
    public static SpriteBatch batch = new SpriteBatch();
    public static BitmapFont font;

    public static void texture(TextureRegion textureRegion, float x, float y, float xx, float yy, float rotation){
        batch.draw(textureRegion.getTexture(), x, y, 0.0f, 0.0f, xx, yy,
                1, 1, rotation,textureRegion.getRegionX(),textureRegion.getRegionY(),textureRegion.getRegionWidth(),textureRegion.getRegionHeight(),false,false);
    }

    public static void texture(TextureRegion textureRegion, float x, float y, float xx, float yy, float rotation, Vector3 color, float alpha){
        batch.setColor(color.x, color.y, color.z, alpha);
        texture(textureRegion,x,y,xx,yy,rotation);
        batch.setColor(1,1,1,1);
    }

    public static void texture(TextureRegion textureRegion, float x, float y, float xx, float yy, float rotation, float alpha){
        batch.setColor(1,1,1, alpha);
        texture(textureRegion,x,y,xx,yy,rotation);
        batch.setColor(1,1,1,1);
    }

    public static void text(String text, float x, float y){
        font.draw(batch, text, x, y, 0, Align.left, false);
    }

    public static void text(String text, float x, float y, Vector3 color, float alpha){
        font.setColor(color.x, color.y, color.z, alpha);
        font.draw(batch, text, x, y, 0, Align.left, false);
        font.setColor(1,1,1,1);
    }

    public static void text(String text, float x, float y, float width){
        font.draw(batch, text, x, y, width, Align.center, false);
    }

    public static void text(String text, float x, float y, float width, Vector3 color, float alpha){
        font.setColor(color.x, color.y, color.z, alpha);
        font.draw(batch, text, x, y, width, Align.center, false);
        font.setColor(1,1,1,1);
    }

    public static void setFontScale(float scl){
        float f=scl*(float) Sc.screenW/2248;
        font.getData().setScale(f,f);
    }

    public static void createCustomFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/kaijuMonster.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        /** The size in pixels */
        parameter.size = 128;
        /** Foreground color (required for non-black borders) */
        parameter.color = Color.WHITE;
        /** Border width in pixels, 0 to disable */
        parameter.borderWidth = 8;
        /** Border color; only used if borderWidth > 0 */
        parameter.borderColor = Color.BLACK;
        /** true for straight (mitered), false for rounded borders */
        parameter.borderStraight = false;
        /** Offset of text shadow on X axis in pixels, 0 to disable */
        parameter.shadowOffsetX = 0;
        /** Offset of text shadow on Y axis in pixels, 0 to disable */
        parameter.shadowOffsetY = 4;
        /** Shadow color; only used if shadowOffset > 0 */
        parameter.shadowColor = new Color(0, 0, 0, 0.75f);
        /** The characters the font should contain */
        //parameter.characters = DEFAULT_CHARS;
        /** Whether the font should include kerning */
        parameter.kerning = true;
        /** The optional PixmapPacker to use */
        parameter.packer = null;
        /** Whether to flip the font vertically */
        parameter.flip = false;
        /** Whether or not to generate mip maps for the resulting texture */
        parameter.genMipMaps = false;
        /** Minification filter */
        parameter.minFilter = Texture.TextureFilter.Linear;
        /** Magnification filter */
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        //Statics.customFont = font12;
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
}
