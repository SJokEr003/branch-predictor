import os,sys
import subprocess

n=1
k=9-n;

while n<9:

	k=9-n

	print '----------------------------------------------\n\n\n',n,k

	with open('CODE', 'r') as myfile:
		prog=myfile.read()
	prog=prog.replace('////<HERE>','int n='+str(n)+',k='+str(k)+' , c=2;');
	# print prog
	
	with open("Predictor1200.java", "w") as text_file:
		text_file.write(prog)
	# os.system('echo \"'+prog+ '\" > Predictor1200.java');

	os.system('ant')

	os.system('ant make-jar')

	for x in xrange(1,5):
		command='java -jar jar/BranchPredictor.jar traces/trace'+str(x)+' 1200'
		output = subprocess.check_output(command, shell=True)

		print command,output

	n+=1
