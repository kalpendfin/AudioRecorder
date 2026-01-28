<p class="demoTitle">&nbsp;</p>
<ol>
<li><strong>Audio Recording</strong>
<ul>
<li>Record Audio from Microphone&nbsp; &nbsp;<span style="color: #339966;">(Completed)</span></li>
<li>Max length : 30 second <strong><span style="text-decoration: underline;"><span style="color: #ff0000; text-decoration: underline;">(Not Completed)</span></span></strong></li>
<li><span style="color: #000000;">Save Locally&nbsp;<span style="color: #339966;">(Completed)</span></span></li>
<li><span style="color: #000000;">Handle Permission Flow properly&nbsp;<span style="color: #339966;">(Completed)</span></span></li>
</ul>
</li>
<li><strong>Audio Feed</strong>
<ul>
<li>Display a vertical scrolling list (RecyclerView / LazyColumn)&nbsp;&nbsp;<span style="color: #339966;">(Completed)</span></li>
<li>Each item represents an audio post&nbsp;&nbsp;<span style="color: #339966;">(Completed)</span></li>
<li>Only one audio play at a time&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style="color: #339966;">(Completed)</span></li>
<li>Scrolling should not cause audio glitches <strong><span style="color: #ff0000;">(Not Checked)</span></strong></li>
</ul>
</li>
<li><span style="color: #000000;"><strong>Audio Playback</strong></span>
<ul>
<li><span style="color: #000000;">Tap an item to play/pause&nbsp; <span style="color: #339966;">(Partially Completed)</span></span></li>
<li><span style="color: #000000;">Audio Should</span>
<ul>
<li><span style="color: #000000;">Continue when scrolling <span style="color: #339966;">(Completed)</span></span></li>
<li><span style="color: #000000;">Stop Correctly when another item is played <span style="color: #339966;">(Completed)</span></span></li>
<li><span style="color: #000000;">Handle app background / foreground&nbsp; transitions gracefully <span style="color: #339966;">(Partiallly Completed)</span></span></li>
</ul>
</li>
</ul>
</li>
<li><span style="color: #000000;">Data Source&nbsp;</span>
<ul>
<li><span style="color: #000000;">Local File - <span style="color: #339966;">Internal Storage used</span></span></li>
</ul>
</li>
</ol>
<p>&nbsp;</p>
<p><span style="color: #ff0000;"><strong>&nbsp; &nbsp;Bonus Part is&nbsp;pending</strong></span></p>
<p>&nbsp;</p>
<ol>
<li>Architechture Decisions
<ul>
<li><span style="color: #339966;">MVVM Pattern used</span></li>
</ul>
</li>
<li>How you manage :
<ul>
<li>Single Audio Playback =&gt;<span style="color: #339966;"> Not externally coded, its managing it self.</span></li>
</ul>
</li>
<li>Tradeoffs you made and why</li>
<li>What you would improve with more time =&gt; <span style="color: #339966;">Will implement incompleted items, for e.g. using Coundown Timer() class we can limit audio for 30 seconds. Also need sometime for checking for scrolling issue.</span></li>
<li>How this would scale in a real app =&gt; <span style="color: #339966;">3 out of 5</span>.</li>
</ol>
<p>&nbsp;</p>
<!-- Comments are visible in the HTML source only -->
