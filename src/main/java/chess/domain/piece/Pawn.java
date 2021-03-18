package chess.domain.piece;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Pawn extends Piece {
    private static final List<Position> INITIAL_BLACK_POSITIONS = Arrays.asList(Position.of('a', '7'),
            Position.of('b', '7'), Position.of('c', '7'), Position.of('d', '7'),
            Position.of('e', '7'), Position.of('f', '7'), Position.of('g', '7'),
            Position.of('h', '7'));
    private static final List<Position> INITIAL_WHITE_POSITIONS = Arrays.asList(Position.of('a', '2'),
            Position.of('b', '2'), Position.of('c', '2'), Position.of('d', '2'),
            Position.of('e', '2'), Position.of('f', '2'), Position.of('g', '2'),
            Position.of('h', '2'));

    public Pawn(Position position, String name) {
        super(position, name);
    }

    @Override
    void move(Position target, CurrentPieces currentPieces) {
        if (this.position.getY() == '7' && this.name.equals("P")) { // 블랙
            if (this.position.subtractY(target) > 0 && this.position.subtractY(target) <= 2) {
                for (int i = 1; i <= position.subtractY(target); i++) {
                    Piece piece = currentPieces.findByPosition(Position.of(position.getX(), (char) (position.getY() - i)));
                    if (!(piece instanceof Empty)) {
                        throw new IllegalArgumentException("[ERROR] 기물을 뛰어 넘어 이동할 수 없습니다.");
                    }
                }
                this.position = target;
                return;
            }
        }

        if (this.position.getY() == '2' && this.name.equals("p")) { // 화이트
            if (target.subtractY(this.position) > 0 && target.subtractY(this.position) <= 2) {
                for (int i = 1; i <= target.subtractY(this.position); i++) {
                    Piece piece = currentPieces.findByPosition(Position.of(position.getX(), (char) (position.getY() + i)));
                    if (!(piece instanceof Empty)) {
                        throw new IllegalArgumentException("[ERROR] 기물을 뛰어 넘어 이동할 수 없습니다.");
                    }
                }
                this.position = target;
                return;
            }
        }

        if (this.position.subtractY(target) == 1 && this.name.equals("P")) { // 블랙
            Piece piece = currentPieces.findByPosition(Position.of(position.getX(), (char) (position.getY() - 1)));
            if (!(piece instanceof Empty)) {
                throw new IllegalArgumentException("[ERROR] 기물을 뛰어 넘어 이동할 수 없습니다.");
            }
            this.position = target;
            return;
        }

        if (target.subtractY(this.position) == 1 && this.name.equals("p")) { // 화이트
            Piece piece = currentPieces.findByPosition(Position.of(position.getX(), (char) (position.getY() + 1)));
            if (!(piece instanceof Empty)) {
                throw new IllegalArgumentException("[ERROR] 기물을 뛰어 넘어 이동할 수 없습니다.");
            }
            this.position = target;
            return;
        }
        throw new IllegalArgumentException("[ERROR] 움직일 수 없는 위치입니다.");
    }

    public static List<Pawn> generate() {
        List<Pawn> blackPawns = INITIAL_BLACK_POSITIONS.stream()
                .map(position -> new Pawn(position, "P"))
                .collect(Collectors.toList());
        List<Pawn> whitePawns = INITIAL_WHITE_POSITIONS.stream()
                .map(position -> new Pawn(position, "p"))
                .collect(Collectors.toList());
        blackPawns.addAll(whitePawns);
        return blackPawns;
    }
}
