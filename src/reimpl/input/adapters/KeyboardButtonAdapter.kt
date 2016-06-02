package input.adapters

import com.badlogic.gdx.Gdx

class KeyboardButtonAdapter(key: Int) : ButtonAdapter(key = key) {

  override fun getSignal(): Boolean {
    return Gdx.input.isKeyPressed(key);
  }

}