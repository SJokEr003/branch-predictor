# branch-predictors
============================
An attempt to implement and test some of the state of the art branch prediction techniques used in modern x86 processors.


Inspired by [prof. S. R. SARANGI](http://www.cse.iitd.ernet.in/~srsarangi/col718_2016/index.html) class.

The folder traces contains five traces of branch instructions against which your predictor is to be tested. The given framework does all that for you.


The files in folder traces/ contain the branch program addresses and the result(taken or not taken) for statndard banchmarks:

	4255370 0
	4255447 0
	4255668 0
	4256520 0
	4256555 0
	4256593 0
	4256769 0
	4257103 0
	4257118 0
	4257126 0
	4257192 0
	4257215 1
	4258894 0
	4259425 1
	4257103 0
	4257118 0
	4257126 0
	4257192 0
	4257215 0
	4257232 0
	4257248 1
	4258666 0
	4685306 0



## [Motivation](http://www.jilp.org/cbp2014/)
The goal for this competition is to compare different branch prediction algorithms in a common framework. Predictors will be evaluated for conditional branches. Predictors must be implemented within a fixed storage budget as specified in the competition rules. The simple and transparent evaluation process enables dissemination of results and techniques to the larger computer architecture community and allows independent verification of results.

The competition will proceed as follows. Contestants are responsible for implementing and evaluating their algorithm in the distributed framework. Submissions will be compiled and run with the original version of the framework. Quantitatively assessing the cost/complexity of predictors is difficult. To simplify the review process, maximize transparency, and minimize the role of subjectivity in selecting a champion, CBP-4 will make no attempt to assess the cost/complexity of predictor algorithms.  All predictors must be implemented within the constraints of the budget for the track of choice. Clear documentation, in the code as well as the paper writeup, must be provided to assure that this is the case. Predictors will be scored on Mispredictions  per thousand instructions (MPKI) only. The arithmetic mean of MPKIs of all 40 traces will be used as the final score of a predictor.

## How to run




## Issues
If there are some issues with the repo, please feel free to push your changes.



## Credits

- Devansh Dalal ([@devanshdalal](https://github.com/devanshdalal))
- Nishant Kumar ([@nish-kr](https://github.com/nish-kr))


## Predictors Used

----------------
![image]()
----------------
![image](https://cloud.githubusercontent.com/assets/5080310/13220989/1814a5d2-d99f-11e5-8a70-5ad338fe5158.png)
----------------


## References

1. http://www.cse.iitd.ernet.in/~srsarangi/col718_2016/lectures/OOOpipelines.pptx

2. http://www.cse.iitd.ernet.in/~srsarangi/col718_2016/papers/branchpred/branchpred2.pdf

3. http://www.cse.iitd.ernet.in/~srsarangi/col718_2016/papers/branchpred/branch-pred-many.pdf

4. http://www.cse.iitd.ernet.in/~srsarangi/col718_2016/papers/branchpred/alternative-impl.pdf
