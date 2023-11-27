from aip import AipSpeech
from playsound import playsound

""" 你的 APPID AK SK """
APP_ID = '你的 App ID'
API_KEY = '你的 Api Key'
SECRET_KEY = '你的 Secret Key'

client = AipSpeech(APP_ID, API_KEY, SECRET_KEY)

result  = client.synthesis('你好 China', 'zh', 1, {
    'vol': 5,
})

# 识别正确返回语音二进制 错误则返回dict
if not isinstance(result, dict):
    with open('audio.mp3', 'wb') as f:
        f.write(result)

# 直接播放
mp3_file_path = 'audio.mp3'
try:
    playsound(mp3_file_path)
except Exception as e:
    print(f'播放发生错误：{e}')
