/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.benchmark.impl.ranking;

import java.io.Serializable;
import java.util.Comparator;

import org.optaplanner.benchmark.impl.result.SingleBenchmarkResult;
import org.optaplanner.core.api.score.Score;

public class TotalScoreSingleBenchmarkRankingComparator implements Comparator<SingleBenchmarkResult>, Serializable {

    private final Comparator<Score> resilientScoreComparator = new ResilientScoreComparator();

    @Override
    public int compare(SingleBenchmarkResult a, SingleBenchmarkResult b) {
        return Comparator
                // Reverse, less is better (redundant: failed benchmarks don't get ranked at all)
                .comparing(SingleBenchmarkResult::hasAnyFailure, Comparator.reverseOrder())
                .thenComparing(SingleBenchmarkResult::getTotalScore, resilientScoreComparator)
                .compare(a, b);
    }

}
