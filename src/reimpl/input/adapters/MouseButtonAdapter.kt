package input.adapters

import com.badlogic.gdx.Gdx

class MouseButtonAdapter(key: Int) : ButtonAdapter(key = key) {

  override fun getSignal(): Boolean {
    return Gdx.input.isButtonPressed(key);
  }

}