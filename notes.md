# My Notes
From now on this file will actually contain **NOTES**, rather than test text like before.
## Java
### Data Types
#### Primitives
* Integers: byte, short, int, long = 1, 2, 4, and 8 bytes respectively. BigInteger class handles larger numbers.
  * These can converted with .toUnsigned\<type\>(), but the arithmatic must be done on the full signed value.
  * 0x... indicates hex. Same as in many other languages. 0... is octal, but that's confusing so be careful.  
* Floats: float, double = 4, 8 bytes respectively
  * Special cases: Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NaN. Check for these using functions (not ==): `Double.isInfinite(d) (positive or negative), Double.isNaN(d)`
* Characters: char = A code unit in UTF-16 encoding. **NOT THE SAME AS IN C++**.
  * Escape characters are the same as in C++ though (at least the main ones \n,\r,\t,\b,\',\\)
* Boolean:
### Randomness
```java
import java.util.random.RandomGenerator
RandomGenerator generator = RandomGenerator.getDefault();
generator.nextInt(); // Generates a random number
```
### JShell
To use, type jshell into terminal. This works fine on windows terminal, I just tested it. It's useful for testing tiny snippets of code without having to make a whole project for them. In fact, I think this will replace a lot of the functionality I've previously relied on test projects for. That's great!

There are a lot of syntax details here, but I think it will be a better use of my time to just look up reference info whenever I need to use something in JShell that I don't know how to use in the future. I'll just include a couple here:
* Tab shows auto-complete options
* $2 means I can reference that value later in my snippet (e.g. 3+$2)
