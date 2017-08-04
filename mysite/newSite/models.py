from django.db import models

# Create your models here.

class MusicItem(models.Model):
	music_id = models.CharField(max_length=100)
	title = models.CharField(max_length=100)
	artist = models.CharField(max_length=100)
	album = models.CharField(max_length=100)

	def __str__(self):
		return str('music_id= '+self.music_id+' title= '+self.title+' artist= '+self.artist+' album= '+self.album)

class Lyric(models.Model):
	music_id = models.ForeignKey(MusicItem,on_delete=models.CASCADE)
	text = models.CharField(max_length=20000)

	def __str__(self):
		return str('music_id= '+self.music_id+' text= '+self.text)