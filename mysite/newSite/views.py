from django.shortcuts import render
from django.http import HttpResponse
from newSite.models import MusicItem, Lyric
from django.http import Http404


# Create your views here.


def index(request):
    return HttpResponse("hello");


def search_title(request, music_title=''):
    try:
        music_list = MusicItem.objects.filter(title=music_title)
        return render(request, 'search_music.html', {'music_list': music_list})
    except MusicItem.DoesNotExist:
        raise Http404('Music does not exist')


def music_all(request):
    try:
        music_list = MusicItem.objects.all()
        return render(request, 'search_music.html', {'music_list': music_list})
    except MusicItem.DoesNotExist:
        raise Http404('Music does not exist')
