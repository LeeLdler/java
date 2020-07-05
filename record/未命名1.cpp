/*
15
12333
2020-07-03 10:17:17
1
1
*/
#include<bits/stdc++.h>
using namespace std;
int main()
{
	FILE *fp=fopen("15to19isGroupfalse.txt","w");
	char a[100]="2020-07-03 10:17:17";
	for(int i=0;i<2000;i++)
	{
		fprintf(fp,"%d\n%d\n%s\n%d\n%d\n",15,12333,a,1,1);
	}
}
