package com.benjfletch.tinytowns.model.score

import com.benjfletch.tinytowns.model.buildings.FedCottage
import com.benjfletch.tinytowns.model.buildings.UnfedCottage
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

class CottageScoringTest: IfFedScoringTest() {
    companion object {
        @JvmStatic
        fun scores(): Stream<Arguments> {
            return Stream.of(
                scores(FedCottage(), 3),
                scores(UnfedCottage(), 0)
            )
        }
    }
}