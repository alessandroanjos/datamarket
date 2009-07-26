update dbo.MICRO_OPERACAO 

set CLASSE =  'com.infinity.datamarket.pdv.op.Op' + SUBSTRING(CLASSE,36,len(CLASSE))

where SUBSTRING(CLASSE,0,36) = 'com.infinity.datamarket.pdv.mic.Mic'
