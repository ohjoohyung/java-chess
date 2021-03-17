package chess.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PositionTest {
    @DisplayName("Position 객체 생성 확인")
    @Test
    void 위치_객체_생성() {
        Position position = Position.of('a', '1');

        assertThat(position.getX()).isEqualTo('a');
        assertThat(position.getY()).isEqualTo('1');
    }

    @DisplayName("체스판에 있는 범위 Position이 아닐 경우")
    @Test
    void 위치_객체_생성_예외_테스트() {
        assertThatThrownBy(() -> Position.of('z', '9'))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
