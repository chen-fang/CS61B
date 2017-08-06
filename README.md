# CS61B
### Unsolved Problems
## Homework4 Hashing
# TestComplexOomage.jave

I cannot fail the testWithDeadlyParams() as is required in this homework. What I understand so far (based on Hint.java) is that bits
are shifted with arithmetic operations, leading to uniform results or multiples of some number.
The defect in the hashcode function in ComplexOomage.java is related to this property. Compared to Hint.java, in addition to multiplying
256 (or 2^8 which pushes bits to left by 8), the hash function adds a number in each iteration. As a result, the solution, or the
deadly params that I am looking for, will negate this effect somehow, making hash function fail due to biased distribution.
