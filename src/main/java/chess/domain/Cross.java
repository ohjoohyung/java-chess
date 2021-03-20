package chess.domain;

import chess.domain.piece.CurrentPieces;
import chess.domain.piece.Position;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Cross {
    UP((source, target) ->
            source.subtractX(target) == 0 && !source.isSubtractYPositive(target),
            new int[]{0, 1}),
    DOWN((source, target) ->
            source.subtractX(target) == 0 && source.isSubtractYPositive(target),
            new int[]{0, -1}),
    RIGHT((source, target) ->
            !source.isSubtractXPositive(target) && source.subtractY(target) == 0,
            new int[]{1, 0}),
    LEFT((source, target) ->
            source.isSubtractXPositive(target) && source.subtractY(target) == 0,
            new int[]{-1, 0});

    private final BiPredicate<Position, Position> findCross;
    private final int[] changeValues;

    Cross(BiPredicate<Position, Position> findCross, int[] changeValues) {
        this.findCross = findCross;
        this.changeValues = changeValues;
    }

    public static Cross findCrossByTwoPosition(Position source, Position target) {
        return Arrays.stream(values())
                .filter(value -> source.isCross(target))
                .filter(value -> value.findCross.test(source, target))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바른 십자 방향이 아닙니다."));
    }

    public void hasPieceInPath(Position source, Position target, CurrentPieces currentPieces) {
        int sourceX = source.getX();
        int sourceY = source.getY();
        int count = Math.max(Math.abs(source.subtractX(target)), Math.abs(source.subtractY(target)));
        for (int i = 1; i < count; i++) {
            validatePieceInPath(currentPieces, sourceX, sourceY, i);
        }
    }

    private void validatePieceInPath(CurrentPieces currentPieces, int sourceX, int sourceY, int count) {
        if (currentPieces.hasSamePositionPiece(
                Position.of((char) (sourceX + (changeValues[0] * count)), (char) (sourceY + (changeValues[1] * count))))) {
            throw new IllegalArgumentException("[ERROR] 기물을 뛰어 넘어 이동할 수 없습니다.");
        }
    }
}
