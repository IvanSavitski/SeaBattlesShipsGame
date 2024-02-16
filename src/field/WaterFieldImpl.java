package field;
import result.Result;

public class WaterFieldImpl implements GameField {
    private boolean isThisFieldHit = false;

    @Override
    public char getIcon() {
        return isThisFieldHit ? 'M' : '~';
    }

    @Override
    public Result shootAt() {
        isThisFieldHit = true;
        return Result.NO_HIT;
    }
}
