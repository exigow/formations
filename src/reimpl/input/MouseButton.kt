package input

import com.badlogic.gdx.Gdx

class MouseButton(key: Int) : Button(key = key) {

  override fun getSignal(): Boolean {
    return Gdx.input.isButtonPressed(key);
  }

}