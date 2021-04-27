ao#ifndef _TERMB_H_
#define _TERMB_H_

#ifdef __cplusplus
extern "C"{
#endif 

//---------------------------------------------------------------------------
//��������غ���
//---------------------------------------------------------------------------
int __stdcall InitComm(int port);
int __stdcall InitCommEx();
int __stdcall CloseComm();
int __stdcall Authenticate();
int __stdcall Read_Content(int active);
int __stdcall Read_Content_Path(char* cPath,int active);
int __stdcall GetSAMID(char *c);
int __stdcall GetCOMBaud(unsigned int *puiBaud);
int __stdcall SetCOMBaud(unsigned int uiCurrBaud, unsigned int  uiSetBaud);
int __stdcall SetBPBoxPort(int nComPort, int nTransPort);
int __stdcall SetAntPower(int nPowerOn);
int __stdcall Buzzer(unsigned char beepTime, unsigned char waitTime, unsigned char beepCount);
int __stdcall ActivateCard(int nReaderSlot, unsigned char *pRecvDatas, int *nRecvLen);
int __stdcall TransApduCommand(int nReaderSlot, unsigned char *pSendDatas, int nSendLen,unsigned char *pRecvDatas, int *nRecvLen);
BOOL __stdcall MakeIDCardImage(char* sHead, char* sIDCardImage);
BOOL __stdcall MakeIDCardImages(char* sHead, char* sIDCardImagePath);
VOID __stdcall DeleteMBCSString(LPSTR str);
BOOL __stdcall GetPhoto(LPBYTE sTmp, LPINT nLen);
BOOL __stdcall GetFingerPrint(LPBYTE sTmp, LPINT nLen);

//---------------------------------------------------------------------------
//���֤��غ���
//---------------------------------------------------------------------------
char* __stdcall GetName(VOID);
char* __stdcall GetSex(VOID);
char* __stdcall GetNation(VOID);
char* __stdcall GetSexCode(VOID);
char* __stdcall GetNationCode(VOID);
char* __stdcall GetBornDate(VOID);
char* __stdcall GetAddress(VOID);
char* __stdcall GetIDNo(VOID);
char* __stdcall GetSignGov(VOID);
char* __stdcall GetStartDate(VOID);
char* __stdcall GetEndDate(VOID);
char* __stdcall GetNewAddress(VOID);

//---------------------------------------------------------------------------
//��������þ���֤��غ���
//---------------------------------------------------------------------------
int __stdcall IsFPRIDCard();//�Ƿ���������þ���֤
char* __stdcall GetFPR_ENName();	//Ӣ������
char* __stdcall GetFPR_SEX();			//�Ա�
char* __stdcall GetFPR_IDNo();		//���þ���֤����
char* __stdcall GetFPR_NationCode();//���������ڵ�������
char* __stdcall GetFPR_Nation();//���������ڵ���
char* __stdcall GetFPR_CHNName();//��������
char* __stdcall GetFPR_ValidStartDate();//֤��ǩ������
char* __stdcall GetFPR_ValidEndDate();//֤����ֹ����
char* __stdcall GetFPR_Birthday();//��������
char* __stdcall GetFPR_IDVersion();//֤���汾��
char* __stdcall GetFPR_IssuingAuthorityCode();//��������������ش���
char* __stdcall GetFPR_IDType();//֤�����ͱ�ʶ,��д��ĸI
char* __stdcall GetFPR_Reserve();//Ԥ��

//---------------------------------------------------------------------------
//�۰�̨�����ס֤��غ���
//---------------------------------------------------------------------------
int __stdcall IsGATIDCard();//�Ƿ�۰�̨�����ס֤
char* __stdcall GetGAT_Name();	//����(30B)
char* __stdcall GetGAT_SEX();			//�Ա�
char* __stdcall GetGAT_SEXCode();			//�Ա����(2B)
char* __stdcall GetGAT_ReserveA();//Ԥ��(4B)
char* __stdcall GetGAT_Birthday();//��������(16B)
char* __stdcall GetGAT_Address();//��ϸ��ַ(70B)
char* __stdcall GetGAT_IDNo();		//�������֤����(36B)
char* __stdcall GetGAT_IssuingAuthority();//ǩ������(30B);
char* __stdcall GetGAT_ValidStartDate();//��Ч��ʼ����
char* __stdcall GetGAT_ValidEndDate();//��Ч��������(16B)
char* __stdcall GetGAT_PassportNo();//ͨ��֤����(18B)
char* __stdcall GetGAT_IssuanceCount();//ǩ������(4B)
char* __stdcall GetGAT_ReserveB();//Ԥ��(6B)
char* __stdcall GetGAT_IDType();//֤�����ͱ�ʶ(2B),��д��ĸJ
char* __stdcall GetGAT_ReserveC();//Ԥ��(6B)

//---------------------------------------------------------------------------
//ISO15693��ǩ��غ���
//---------------------------------------------------------------------------
int __stdcall ISO15693_ReadSingleBlock(bool readSecSta, int nBlkAddr, unsigned char bufBlockDat[], int nSize, int *bytesBlkDatRead) ;
int __stdcall ISO15693_WriteSingleBlock(int blkAddr,unsigned char newBlkData[],int bytesToWrite);
int __stdcall ISO15693_ReadMultiBlocks(bool readSecSta, int nBlkAddr, int numOfBlksToRead, int *numOfBlksRead, unsigned char bufBlockDat[], int nSize, int *bytesBlkDatRead) ;
int __stdcall ISO15693_WriteMultipleBlocks(int blkAddr,int numOfBlks, unsigned char newBlkData[],int bytesToWrite);
int __stdcall ISO15693_GetBlockSecStatus(int blkAddr, int numOfBlks, unsigned char bufBlkSecs[], int nSize, int *bytesSecRead);
int __stdcall ISO15693_GetSystemInfo(unsigned char uid[], unsigned char *dsfid, unsigned char *afi, int *blkSize ,int *numOfBloks, unsigned char* icRef);
int __stdcall ISO15693_LockMultipleBlocks(int blkAddr, int numOfBlks);
int __stdcall ISO15693_WriteAFI(unsigned char afi);
int __stdcall ISO15693_LockAFI();
int __stdcall ISO15693_WriteDSFID(unsigned char dsfid);
int __stdcall ISO15693_LockDSFID();
int __stdcall ISO15693_Inventory(unsigned char afi, unsigned char bufUids[], int nSize, int *bytesUidNums);

//---------------------------------------------------------------------------
//ISO7816��غ���
//---------------------------------------------------------------------------
int __stdcall ISO7816_CardInsert(int nSlot);
int __stdcall ISO7816_CardRemove(int nSlot);
int __stdcall ISO7816_TransApduCommand(int nReaderSlot, unsigned char *pSendDatas, int nSendLen, unsigned char *pRecvDatas, int *nRecvLen);

//---------------------------------------------------------------------------
//ISO14443��غ���
//---------------------------------------------------------------------------
int __stdcall ISO14443_TransApduCommand(int nReaderSlot, unsigned char *pSendDatas, int nSendLen, unsigned char *pRecvDatas, int *nRecvLen);

//---------------------------------------------------------------------------
//MIFARE����غ���
//---------------------------------------------------------------------------
int __stdcall MIFARE_Authentication(unsigned char bSectorNo, unsigned char *pbKeyA, unsigned char *pbKeyB);
int __stdcall MIFARE_ReadData(unsigned char bBlock, unsigned char *data, int nLen);
int __stdcall MIFARE_WrtieData(unsigned char bBlock, unsigned char *data, int nLen);
int __stdcall MIFARE_IncrementValue(unsigned char bBlock, unsigned int nValue);
int __stdcall MIFARE_DecrementValue(unsigned char bBlock, unsigned int nValue);
int __stdcall MIFARE_ReadValue(unsigned char bBlock, unsigned int *pnValue);
int __stdcall MIFARE_InitValue(unsigned char bBlock, unsigned int nValue);
int __stdcall MIFARE_WriteSectorTrailer(unsigned char bBlock, unsigned char *pbKEYA, unsigned char *pbKEYB, unsigned char *pbAccessBits);


//---------------------------------------------------------------------------
//SDTAPI��غ���
//---------------------------------------------------------------------------
int __stdcall SDT_SetMaxRFByte(int iPortID,unsigned char ucByte,int bIfOpen);
int __stdcall SDT_GetCOMBaud(int iComID,unsigned int *puiBaud);
int __stdcall SDT_SetCOMBaud(int iComID,unsigned int  uiCurrBaud,unsigned int  uiSetBaud);
int __stdcall SDT_ClosePort(int iPortID);
int __stdcall SDT_OpenPort(int iPortID);
int __stdcall SDT_GetErrorString(int ErrorCode, char * ErrorString);
int __stdcall SDT_GetSAMStatus(int iPortID,int iIfOpen);
int __stdcall SDT_ResetSAM(int iPortID,int iIfOpen);
int __stdcall SDT_GetSAMID(int iPortID,unsigned char *pucSAMID,int iIfOpen);
int __stdcall SDT_GetSAMIDToStr(int iPortID,char *pcSAMID,int iIfOpen);
int __stdcall SDT_StartFindIDCard(int iPortID,unsigned char *pucIIN,int iIfOpen);
int __stdcall SDT_SelectIDCard(int iPortID,unsigned char *pucSN,int iIfOpen);
int __stdcall SDT_ReadBaseMsg(int iPortID,unsigned char * pucCHMsg,unsigned int *	puiCHMsgLen,unsigned char * pucPHMsg,unsigned int  *puiPHMsgLen,int iIfOpen);
int __stdcall SDT_ReadBaseMsgToFile(int iPortID,char * pcCHMsgFileName,unsigned int *	puiCHMsgFileLen,char * pcPHMsgFileName,unsigned int  *puiPHMsgFileLen,int iIfOpen);
int __stdcall SDT_ReadNewAppMsg(int iPortID,unsigned char * pucAppMsg,unsigned int *	puiAppMsgLen,int iIfOpen);
int	__stdcall SDT_ReadBaseFPMsg(int iPort, unsigned char *pucCHMsg, unsigned int *puiCHMsgLen, unsigned char *pucPHMsg, unsigned int *puiPHMsgLen, unsigned char *pucFPMsg, unsigned int *puiFPMsgLen, int iIfOpen);
int	__stdcall SDT_ReadBaseFPMsgToFile(int iPort, char *pcCHMsgFileName, unsigned int *puiCHMsgFileLen, char *pcPHMsgFileName, unsigned int *puiPHMsgFileLen, char *pcFPMsgFileName, unsigned int *puiFPMsgFileLen, int iIfOpen);

#ifdef __cplusplus
}
#endif 

#endif