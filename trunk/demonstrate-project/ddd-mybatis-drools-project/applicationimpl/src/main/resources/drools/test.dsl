package org.drools.examples.helloworld
import java.util.*

rule "Hello World"
    when
        c : Map(this["tail_number"]=="B2080")
        d : Map(this["tail_number"]=="B6909")
    then
        System.out.println("first: " + c );
        System.out.println("first: " + d );
end

rule "Good Bye"
    when
        c : Map(this["tail_number"]=="B6909")
        d : Map(this["tail_number"]=="B2081")
    then
        System.out.println("second: " + c );
        System.out.println("second: " + d );
end;